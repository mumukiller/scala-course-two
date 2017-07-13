import scalikejdbc.{GlobalSettings, LoggingSQLAndTimeSettings}

/**
  * Created by g.gerasimov on 20.04.2017.
  */
object ApplicationConfiguration {

  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(
    enabled = true,
    singleLineMode = true,
    printUnprocessedStackTrace = false,
    stackTraceDepth = 15,
    logLevel = 'info,
    warningEnabled = false,
    warningThresholdMillis = 3000L,
    warningLogLevel = 'warn
  )
}
