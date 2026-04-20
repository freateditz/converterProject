#!/usr/bin/env sh
set -eu

# One-click launcher for Number Converter Pro
SCRIPT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
cd "$SCRIPT_DIR"

if [ "${1:-}" = "--compile-only" ]; then
  if command -v mvn >/dev/null 2>&1; then
    echo "Compiling with Maven..."
    mvn -q clean compile
  else
    echo "Maven not found. Compiling with javac..."
    mkdir -p out
    javac -d out $(find src/main/java -name '*.java')
  fi
  echo "Compile complete."
  exit 0
fi

if command -v mvn >/dev/null 2>&1; then
  echo "Starting app with Maven..."
  mvn -q clean compile exec:java
else
  echo "Maven not found. Falling back to javac + java..."
  mkdir -p out
  javac -d out $(find src/main/java -name '*.java')
  java -cp out com.converter.Main
fi
