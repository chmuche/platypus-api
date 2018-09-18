package org.platypus.v2.db.cr

import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.model.field.api.DbFieldConverter
import java.sql.Statement
import java.util.*

interface StatementInterceptor {
    fun beforeExecution(context: StatementContext) {}
    fun afterExecution(contexts: List<StatementContext>, executedStatement: Statement, delta: Long) {}
    fun onError(contexts: List<StatementContext>, e: Throwable) {}
}

class StatementMonitor {
    private val interceptors: MutableList<StatementInterceptor> = arrayListOf()

    fun register(interceptor: StatementInterceptor) = interceptors.add(interceptor)
    fun unregister(interceptor: StatementInterceptor) = interceptors.remove(interceptor)

    fun notifyBeforeExecution(context: List<StatementContext>) {
        for (ctx in context) {
            for (interceptor in interceptors) {
                interceptor.beforeExecution(ctx)
            }
        }
    }

    fun notifyAfterExecution(executedContexts: List<StatementContext>, executedStatement: Statement, delta: Long) {
        interceptors.forEach { it.afterExecution(executedContexts, executedStatement, delta) }
    }

    fun notifyOnError(executedContexts: List<StatementContext>, e: Throwable) {
        interceptors.forEach { it.onError(executedContexts, e) }
    }
}

class StatementContext(val statement: SqlStatement<*>, val args: Iterable<Pair<DbFieldConverter, Any?>>) {
    fun sql() = statement.prepareSQL()

    fun expandArgs(): String {
        val sql = sql()
        val iterator = args.iterator()
        if (!iterator.hasNext())
            return sql

        return buildString {
            val quoteStack = Stack<Char>()
            var lastPos = 0
            for (i in 0..sql.length - 1) {
                val char = sql[i]
                if (char == '?') {
                    if (quoteStack.isEmpty()) {
                        append(sql.substring(lastPos, i))
                        lastPos = i + 1
                        val (col, value) = iterator.next()
                        append(col.convertToString(value))
                    }
                    continue
                }

                if (char == '\'' || char == '\"') {
                    if (quoteStack.isEmpty()) {
                        quoteStack.push(char)
                    } else {
                        val currentQuote = quoteStack.peek()
                        if (currentQuote == char)
                            quoteStack.pop()
                        else
                            quoteStack.push(char)
                    }
                }
            }

            if (lastPos < sql.length)
                append(sql.substring(lastPos))
        }
    }
}

interface SqlLogger {
    fun log(context: StatementContext, env: PlatypusEnvironment, delta: Long)
    fun log(s: Any, delta: Long)
    fun logError(s: Any, e: Throwable? = null)
}

private class StoutPrint : SqlLogger{
    override fun log(context: StatementContext, env: PlatypusEnvironment, delta: Long) {
        println("${context.sql()} t:$delta")
    }

    override fun log(s: Any, delta: Long) {
        println("$s t:$delta")
    }

    override fun logError(s: Any, e: Throwable?) {
        println("ERROR: $s ${e?.message}")
    }
}

class CompositeSqlLogger : SqlLogger, StatementInterceptor {
    private val loggers: ArrayList<SqlLogger> = ArrayList(2)
    init {
        register(StoutPrint())
    }

    fun register(logger: SqlLogger) {
        loggers.add(logger)
    }

    fun removeLogger(logger: SqlLogger) {
        loggers.remove(logger)
    }

    override fun log(context: StatementContext, env: PlatypusEnvironment, delta: Long) = loggers.forEach { it.log(context, env, delta) }

    override fun log(s: Any, delta: Long) = loggers.forEach { it.log(s, delta) }

    override fun logError(s: Any, e: Throwable?) = loggers.forEach { it.logError(s, e) }

    override fun beforeExecution(context: StatementContext) {}

    override fun afterExecution(contexts: List<StatementContext>, executedStatement: Statement, delta: Long) {
        contexts.forEach {
            log(it.expandArgs(), delta)
        }
    }

    override fun onError(contexts: List<StatementContext>, e: Throwable) {
        contexts.forEach {
            log(it.expandArgs(), -1)
        }
    }
}