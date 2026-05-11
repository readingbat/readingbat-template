.PHONY: default help clean build tests uberjar uber cc run heroku logs lint detekt \
		detekt-baseline format versioncheck upgrade-wrapper _require-gradle-version

GRADLE_VERSION := $(shell grep -E '^gradle[[:space:]]*=' gradle/libs.versions.toml | sed -E 's/.*"([^"]+)".*/\1/')

default: versioncheck

help: ## Show this help
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "  \033[36m%-22s\033[0m %s\n", $$1, $$2}' $(MAKEFILE_LIST)

clean: ## Remove build artifacts
	./gradlew clean

build: ## Build the project (skips tests)
	./gradlew build -x test

lint: ## Run Kotlinter and detekt
	./gradlew lintKotlin detekt

format: ## Apply kotlinter (ktlint) auto-formatting
	./gradlew formatKotlin

detekt: ## Run detekt static analysis
	./gradlew detekt

detekt-baseline: ## Generate detekt baseline file
	./gradlew detektBaseline

tests: ## Run all tests
	./gradlew --rerun-tasks check

uberjar: ## Build the fat JAR (build/libs/server.jar)
	./gradlew buildFatJar

uber: uberjar ## Build and run the fat JAR
	java -jar build/libs/server.jar

cc: ## Continuous compile (rebuilds on file changes, skips tests)
	./gradlew build --continuous -x test

run: ## Run the server
	./gradlew run

heroku: ## Deploy to Heroku (git push heroku master)
	git push heroku master

logs: ## Tail Heroku logs
	heroku logs --tail

versioncheck: ## Check for dependency updates
	./gradlew dependencyUpdates

# Gradle's documented upgrade procedure: the first run rewrites
# gradle-wrapper.properties using the *old* wrapper jar; the second run
# regenerates the wrapper itself with the new version.
upgrade-wrapper: _require-gradle-version ## Upgrade the Gradle wrapper to the version in libs.versions.toml
	./gradlew wrapper --gradle-version=$(GRADLE_VERSION) --distribution-type=bin
	./gradlew wrapper --gradle-version=$(GRADLE_VERSION) --distribution-type=bin

_require-gradle-version:
	@[ -n "$(GRADLE_VERSION)" ] || { echo "ERROR: Could not determine gradle version from gradle/libs.versions.toml" >&2; exit 1; }
