package finda.findanotification.adapter.out.persistence

interface GenericMapper<D, E> {

    fun toDomain(entity: E?): D?

    fun toEntity(domain: D): E
}
