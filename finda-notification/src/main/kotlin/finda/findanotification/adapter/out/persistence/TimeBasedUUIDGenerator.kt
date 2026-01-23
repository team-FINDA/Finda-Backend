package finda.findanotification.adapter.out.persistence

import com.fasterxml.uuid.Generators
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable
import java.util.UUID

class TimeBasedUUIDGenerator : IdentifierGenerator {
    override fun generate(session: SharedSessionContractImplementor?, entity: Any?): Serializable {
        return Generators.timeBasedGenerator().generate() as UUID
    }
}
