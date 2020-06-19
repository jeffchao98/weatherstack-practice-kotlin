# weatherstack-practice-kotlin
Practice android project in kotlin with integrate weatherstack api

## Setup steps

### 1. API Token

This repo requires the available `API access key` from weatherstack.com, you can get one via register an account with the free plan.

After you have one, please find in gradle.properties, where you can find this file at:
- If you are the macOS user, you can find it in the path `~/.gradle` , if not, please create one
- You can also find the same file in the root path of this project folder `${repo path}/WtrStkPractice/`

Open the file, add the line with the format `WEATHERSTACK_TOKEN = "${your API access key}"` , as the following example

```
WEATHERSTACK_TOKEN = "abcde12345"
```

### 2. Build and run

This project have no gradle config for production, so you can just easily build and run with the emulator or connected device.

If you prefer get the debug build, please directly execute the assemble cmd as following

```
./gradlew assembleDebug
```

Than you can find the apk file from the project folder

Enjoy ~ !!