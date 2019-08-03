# GitHub Repositories

App made in Android Studio that uses the GitHub Api to get repositories and commits from an especific user, by reading a QR Code.

It reads a QR Code holding an url to the github api, and shows the list of repositories of the given user. Clicking in a repository will show a list of the 5 last commits made.

## Usage

It's very simple to use, for the QR Code to be compatible it needs to hold an url with ther following format: ```https://api.github.com/users/{username} ```

To make an QR Code any QR Code Generator website can be used, for example [this one](https://www.the-qrcode-generator.com/)

After the creation of the QR Code, just click the "Scan QR Code" button in the starting screen, and a list of repositories will be shown

## Things that still need to be done

- I couldn't find a way to control changing pages of the repositories in the api, so the app is limited to showing a maximum of 30 repositories per user, I still have to figure ouut how to solve this issue in users with lots of repositories.

- I am just considering commits in the master branch, so for future updates I will consider getting the latest branches made a commit, or even show a list of latest commits by branch.

## Tecnical Info

- The app was made using the Android Minimum API 15: Android 4.0.3 (IceCreamSandwich), any phone or emulator should run the app with no problems

- I used Google's Volley module to make requests and process the JSON, Picasso module to get images from the internet and zxing module to access camera and read QR Code.

- An .apk file to install the app on a phone or emulator is available in ```.\app\build\outputs\apk\debug ```