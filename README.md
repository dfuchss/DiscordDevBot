# DiscordDevBot - A bot that helps you developing other bots

This repo contains my DevBot for [Discord](https://discordapp.com/) bot.

## Requirements (Development):

* Java 11 and Maven

## Run the Bot (via Docker)

You'll need the following two things:

* The possibility to run docker containers
* A Discord Bot Account (and of course the Token)

To start the bot simply run:

* Store token to environment: `echo "DISCORD_TOKEN=YOUR-Discord-TOKEN" > .env`
* Start the Bot: `docker run -itd --name "DevBot" --env-file .env ghcr.io/dfuchss/discord-devbot`
