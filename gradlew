#!/usr/bin/env sh

APP_HOME=$(cd "$(dirname "$0")" && pwd)
JAVACMD=java
exec "$JAVACMD" -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
