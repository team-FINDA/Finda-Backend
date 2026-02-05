package finda.findaauth.adapter.out.persistence.auth.repository

import finda.findaauth.adapter.out.persistence.auth.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : CrudRepository<RefreshToken, String>
