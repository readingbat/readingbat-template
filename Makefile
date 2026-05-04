.PHONY: default clean build tests uberjar uber cc run heroku logs versioncheck upgrade-wrapper

default: versioncheck

clean:
	./gradlew clean

build:
	./gradlew build -xtest

tests:
	./gradlew --rerun-tasks check

uberjar:
	./gradlew uberjar

uber: uberjar
	java -jar build/libs/server.jar

cc:
	./gradlew build --continuous -xtest

run:
	./gradlew run

heroku:
	git push heroku master

logs:
	heroku logs --tail

versioncheck:
	./gradlew dependencyUpdates

upgrade-wrapper:
	./gradlew wrapper --gradle-version=9.5.0 --distribution-type=bin
