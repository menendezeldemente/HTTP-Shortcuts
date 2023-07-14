package ch.rmy.android.http_shortcuts.data.enums

sealed class HostVerificationConfig {
    object Default : HostVerificationConfig()

    data class SelfSigned(
        val expectedFingerprint: ByteArray,
    ) : HostVerificationConfig() {
        override fun equals(other: Any?) =
            (other as? SelfSigned)?.expectedFingerprint?.contentEquals(expectedFingerprint) == true

        override fun hashCode() =
            expectedFingerprint.contentHashCode()
    }

    object TrustAll : HostVerificationConfig()
}
