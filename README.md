#!/bin/bash

# Directory where your server files will be located
SERVER_DIR="/mnt/server"

# Name of the JAR file
JAR_NAME="Server.jar"

# Install Git
apt-get update && apt-get -y install git

# Create server directory
mkdir -p "$SERVER_DIR"
cd "$SERVER_DIR"

# Clone the GitHub repository
echo "Cloning GuessWhoServerEgg from GitHub"
git clone --single-branch --branch master https://github.com/ShadowRL76/GuessWhoServerEgg.git .

# Build the Java project
echo "Building GuessWhoServerEgg"
./gradlew build


echo "Installation complete. Congratulations!"




This script installs Git, creates a server directory, clones the GitHub repository, builds the Java project using Gradle, and prints a completion message.

Just a note: Make sure that the `gradlew` script is executable and properly located in your project directory. If it's not, you might encounter a "No such file or directory" error when trying to build the Java project.
