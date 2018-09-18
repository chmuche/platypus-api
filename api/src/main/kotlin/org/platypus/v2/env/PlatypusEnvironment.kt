package org.platypus.v2.env

import org.platypus.v2.db.cr.StatementExecutor
import org.platypus.v2.db.cr.Transaction
import org.platypus.v2.model.Model
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.repo.RecordRepository
import org.platypus.v2.security.PlatypusUser
import org.platypus.v2.utils.ContextAble
import org.platypus.v2.utils.SudoAble
import java.time.ZoneId
import java.util.logging.Logger

interface PlatypusEnvironment : ReadOnlyPlatypusEnvironment, SudoAble<PlatypusEnvironment>, ContextAble<PlatypusEnvironment> {

    /**
     * Store all the altered and new data to the DataBase
     */
    fun flush()

    /**
     * Store all the altered and new data to the DataBase for this model
     */
    fun flush(model: Model<*>)


    /**
     * After the call you can't call [flush]
     */
    override fun close()

    /**
     * Return a new Environement with [envUser] = [user]
     * [sudoUser] is reset
     * [context] is a copy of the current
     * [lang] = [user].language
     * [timezone] = [user].zoneId
     */
    fun connect(user: PlatypusUser): PlatypusEnvironment

    val cr: Transaction

}

interface ReadOnlyPlatypusEnvironment : AutoCloseable {
    /**
     * Return the value passed to the configuration
     * @see org.platypus.api.config.PlatypusConf.debug
     */
    val debug: Boolean
    /**
     * The current context of the environement
     */
    val context: PlatypusContext
    /**
     * The current user who trigger the current action
     */
    val envUser: PlatypusUser
    /**
     * The sudo user work like in Unix, GNU/Linux Os.
     * If [sudoUser] and [envUser] are not the same the [sudoUser] is used to check the access
     * if the [envUser] don't have the minimal grant to perform the wanted action
     *
     * [sudoUser] return the current sudo user or the same value of the [envUser] property
     */
    val sudoUser: PlatypusUser
    /**
     * The timezone of the current User
     * If the [sudoUser] is set the timezone don't change
     */
    val timezone: ZoneId
    /**
     * A generic logger if you wan't to log somethings is the application log
     */
    val logger: Logger

    fun <M : Model<M>> repoOf(model: M): RecordRepository<M>

    fun <M : Model<M>> emptyRecordOf(model: M): Record<M>
}