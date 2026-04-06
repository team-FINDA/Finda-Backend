package finda.findabatch.infra.event.dto.volunteer

import java.time.LocalTime

/**
 * Debezium CDC로 캡처된 MySQL TIME 타입은 두 가지 형식으로 전달될 수 있음
 * - "09:00:00" 형식: 일반 문자열
 * - "32400000000" 형식: 마이크로초 단위 숫자 문자열
 *
 * 두 형식을 모두 LocalTime으로 변환하기 위한 확장함수
 */

fun String.toLocalTime(): LocalTime {
    return if (this.contains(":")) {
        LocalTime.parse(this)
    } else {
        LocalTime.ofSecondOfDay(this.toLong() / 1_000_000)
    }
}
