import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

logFileRootPath = '/install/log/MCHMS/'

logPattern = '[%d{HH:mm:ss}][%5p][%-20F:%-3L][%X{URI}][%X{ID}][%t] %m%n'

def fileDatePattern = 'yyyy-MM-dd'

appender('debug_log', RollingFileAppender) {
    encoder(PatternLayoutEncoder) { pattern = logPattern }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = logFileRootPath + "debug.log.%d{$fileDatePattern}"
        maxHistory = 7
    }
}


appender('root_log', RollingFileAppender) {
    encoder(PatternLayoutEncoder) { pattern = '[%d{HH:mm:ss}][%t] %m%n' }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = logFileRootPath + "root.log.%d{$fileDatePattern}"
        maxHistory = 7
    }
}

logger('com.jeonbuk', debug, ['debug_log'], false)
root(error, ['root_log'])
