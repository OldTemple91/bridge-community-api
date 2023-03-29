package com.bridge.community.utils

import com.bridge.community.exception.BizBaseException
import com.bridge.community.exception.ErrorCode
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

@Component
class JwtGenerator {

    fun createJwt(memberId: Long): String {
        val now = Date()
        return Jwts.builder()
            .setHeaderParam("type", "jwt")
            .claim("memberId", memberId)
            .setIssuedAt(now)
            .setExpiration(Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365))
            .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
            .compact()
    }

    fun getJwt(): String {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        return request.getHeader("ACCESS-TOKEN")
    }

    @Throws(BizBaseException::class)
    fun getMemberInfoByJwt(): Long {
        //1. JWT 추출
        val accessToken = getJwt()
        if (accessToken.isEmpty()) {
            throw BizBaseException(ErrorCode.EMPTY_JWT)
        }

        // 2. JWT parsing
        val claims: Jws<Claims> = try {
            Jwts.parser()
                .setSigningKey(Secret.JWT_SECRET_KEY)
                .parseClaimsJws(accessToken)
        } catch (ignored: Exception) {
            throw BizBaseException(ErrorCode.INVALID_JWT)
        }

        return claims.body.get("memberId", Integer::class.java).toLong()
    }
}