#!/bin/sh

BASEDIR="$(cd "$(dirname "$0")"; pwd -P)"
WRAPPER_DIR="$BASEDIR/.mvn/wrapper"
WRAPPER_PROPS="$WRAPPER_DIR/maven-wrapper.properties"
WRAPPER_JAR="$WRAPPER_DIR/maven-wrapper.jar"

if [ ! -f "$WRAPPER_PROPS" ]; then
  echo "[ERROR] Missing $WRAPPER_PROPS"
  exit 1
fi

if [ ! -f "$WRAPPER_JAR" ]; then
  DOWNLOAD_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
  WRAPPER_URL="$(sed -n 's/^wrapperUrl=//p' "$WRAPPER_PROPS")"
  if [ -n "$WRAPPER_URL" ]; then
    DOWNLOAD_URL="$WRAPPER_URL"
  fi

  echo "Downloading Maven Wrapper JAR..."
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL -o "$WRAPPER_JAR" "$DOWNLOAD_URL"
  elif command -v wget >/dev/null 2>&1; then
    wget -q -O "$WRAPPER_JAR" "$DOWNLOAD_URL"
  else
    echo "[ERROR] curl or wget required to download wrapper jar"
    exit 1
  fi
fi

JAVA_EXE="java"
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
  JAVA_EXE="$JAVA_HOME/bin/java"
fi

exec "$JAVA_EXE" -classpath "$WRAPPER_JAR" "-Dmaven.multiModuleProjectDirectory=$BASEDIR" org.apache.maven.wrapper.MavenWrapperMain "$@"
#!/bin/sh
BASEDIR=
WRAPPER_DIR="/.mvn/wrapper"
WRAPPER_PROPS="/maven-wrapper.properties"
WRAPPER_JAR="/maven-wrapper.jar"

if [ ! -f "" ]; then
  echo "[ERROR] Missing "
  exit 1
fi

if [ ! -f "" ]; then
  DOWNLOAD_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
  if command -v awk >/dev/null 2>&1; then
    WRAPPER_URL=
    if [ -n "" ]; then
      DOWNLOAD_URL=""
    fi
  fi

  echo "Downloading Maven Wrapper JAR..."
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL -o "" ""
  elif command -v wget >/dev/null 2>&1; then
    wget -q -O "" ""
  else
    echo "[ERROR] curl or wget required to download wrapper jar"
    exit 1
  fi
fi

JAVA_EXE="java"
if [ -n "" ] && [ -x "/bin/java" ]; then
  JAVA_EXE="/bin/java"
fi

exec "" -classpath "" "-Dmaven.multiModuleProjectDirectory=" org.apache.maven.wrapper.MavenWrapperMain "$@"
