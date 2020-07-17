[![Kotlin](https://img.shields.io/badge/%20language-Kotlin-red.svg)](https://kotlinlang.org/)

# ReadingBat Template

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/readingbat/readingbat-template)
[![Run on Repl.it](https://repl.it/badge/github/readingbat/readingbat-template)](https://repl.it/github/readingbat/readingbat-template)

The underlying framework for this is the [readingbat-core](https://github.com/readingbat/readingbat-core) repo.

## Content

ReadingBat content is described with a Kotlin DSL. Using the DSL requires very little knowledge of Kotlin.

ReadingBat supports challenges written in 3 languages: Python, Java and Kotlin.

## Setup

Click on the [![](docs/template_button.png)](https://github.com/readingbat/readingbat-template/generate) 
button above to clone the template repo and create your own ReadingBat content.

## Running

The server can be run in two modes: debug mode and production.
Debug mode allows you to edit content and see the updates in the browser without
restarting the server.

### Debug Mode

1) Uncomment the ```ktor.deployment.watch``` variable in [application.conf](./src/main/resources/application.conf#L31).

2) Compile the code continuously with: ```make cc``` in a terminal window.

3) Run the server with: ```make run``` in a different terminal window.

4) View your content at http://0.0.0.0:8080


### Production

1) Comment out the ```ktor.deployment.watch``` variable in [application.conf](./src/main/resources/application.conf#L31)

2) Run the server locally with: 
```make uber```

You can also deploy the server 




The `includeFiles` or `includeFilesWithType` properties require a 
[Github personal access token](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line).