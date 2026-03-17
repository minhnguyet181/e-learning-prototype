#!/bin/sh
# Apache Maven Wrapper startup script, version 3.3.2
# Required: JAVA_HOME or java on PATH

MVNW_REPOURL="${MVNW_REPOURL:-https://repo.maven.apache.org/maven2}"
BASE_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"

WRAPPER_JAR="$BASE_DIR/.mvn/wrapper/maven-wrapper.jar"
WRAPPER_PROPERTIES="$BASE_DIR/.mvn/wrapper/maven-wrapper.properties"

if [ ! -r "$WRAPPER_PROPERTIES" ]; then
  echo "Missing $WRAPPER_PROPERTIES" >&2
  exit 1
fi

if [ ! -r "$WRAPPER_JAR" ]; then
  echo "Downloading Maven wrapper jar..." >&2
  mkdir -p "$BASE_DIR/.mvn/wrapper" || exit 1
  WRAPPER_URL="$MVNW_REPOURL/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar"
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$WRAPPER_URL" -o "$WRAPPER_JAR" || exit 1
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$WRAPPER_URL" -O "$WRAPPER_JAR" || exit 1
  else
    echo "Need curl or wget to download Maven wrapper." >&2
    exit 1
  fi
fi

JAVA_EXE="${JAVA_HOME:+$JAVA_HOME/bin/}java"
exec "$JAVA_EXE" -jar "$WRAPPER_JAR" --settings "$BASE_DIR/.mvn/wrapper/settings.xml" "$@"
