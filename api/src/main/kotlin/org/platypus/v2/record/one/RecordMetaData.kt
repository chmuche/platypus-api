package org.platypus.v2.record.one

import org.platypus.v2.security.PlatypusUser
import org.platypus.v2.utils.Identifiable
import java.time.LocalDateTime

interface RecordMetaData : Identifiable{
    /**
     * The string representation of the current Record
     */
    val displayName: String
    /**
     * The creation date of this record, the [LocalDateTime.now] is used when this record is created
     */
    val createDate: LocalDateTime
    /**
     * The last write date of this record, the [LocalDateTime.now] is used when this record is created
     */
    val writeDate: LocalDateTime
    /**
     * The [ReadOnlyPlatypusEnvironment.envUser] who create this record
     */
    val createUid: PlatypusUser
    /**
     * The [ReadOnlyPlatypusEnvironment.envUser] who update the last time this record
     */
    val writeUid: PlatypusUser
    /**
     * The external refeterence to used in the method [Repository.byRef]
     */
    val externalRef: String?
}