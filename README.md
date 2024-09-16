# Hello!

Welcome to Umain's Test Automation Assignment! This document contains the following things:

- Intro to the Work Test
- Requirements
- Technical specifications

## Intro to the Work Test

Umain is working on setting up a new and exciting restaurant service: Munchies! For all your restaurant needs! We have a design and develop builds ready. Now we only need automation test coverage.

## Requirements

### User stories
- As a user, the first time I open the application / website, I should see an unfiltered list of all restaurants
- As a user, I should be able to select a filter from the sidebar (website only) or the topbar. When I select a filter, the List View should reflect the new updates
- As a user, I should be able to select multiple filters
- As a user, I should be able to deselect a filter by clicking on it. If I deselect any filter(s), the List View should reflect the new updates

### Create test plan

As we are close to a big release, we will cover only critical paths. Write some test cases to cover the most critical functionality. We have 3 different projects: Android, iOS and web.
- Choose 2 of the 3 projects and write the test cases for them

### Automation
To save us time in the future, it would be really helpful to automate the tests.
- Automate the test cases you have written for the projects

### Test Reporting (Optional)
If time allows it, we would like to see a Test Report of 1 of the Test Runs. You're free to choose which tools you'd like to use for this.

## Umain's Tech Stack and YOU!

At Umain, our main test automation stack is Appium, JavaScript and WDIO for mobile and Cypress, WDIO, Playwright, or Puppeteer for Web. 
For this Work Test, you can choose any tech stack you are comfortable with.

## What's included in this Repository

In this repository you'll find an Android, iOS, and web project, all about the same basic "Munchies" app. The Android project is made with Jetpack Compose and Kotlin Multiplatform. The iOS project with SwiftUI and the web project with Next.js 

You can find a prebuild android apk in 
```
Android/androidApp/build/outputs/apk/androidTest/debug/androidApp-debug-androidTest.apk
```

You can find a prebuild iOS ipa in 
```
iOS/umain-cc-testBuild/umain-cc.ipa
```
the bundle id of this app is com.umain.qa-test


## Design
You can find the [link to the design here](https://www.figma.com/file/263XJno7ii0uEaarJP9Ydw/Umain-Tech-Case?type=design&node-id=27%3A5682&mode=design&t=BPI3BgkmmHVtTdCb-1).
There are 2 pages in Figma, called App and Web. App is used for the mobile view. Web is used for the desktop view.
To get the right fonts, colors, sizes and more, please refer to the design in Figma. You should be able to get this info from the components and styles.

## All done?

:star: Awesome, great job! :star:
The next thing is that a Umain QA engineer will take a look at your project, code, testcases and anything else you worked on!
Here are a few ways to get your project to us:

- You could share your repo (if public) so that we can clone and install locally
- You could ZIP your project and send it over
- Share it from a Cloud solution to Umain (e.g. Google Drive)

## Questions?

Don't hesistate to contact us if you have any questions! Please send an email to either:

- doortje.spanjerberg@umain.com
- cas.luijtelaar@umain.com
