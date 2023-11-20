# Post Tech Challenge

## Overview

The app allow users to view a list of posts, view details of a specific post, and create new posts made on kotlin and Jetpack Compose

## Table of Contents

- [Getting Started](#getting-started)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Dependencies](#dependencies)
- [Architecture](#architecture)
- [Caching](#caching)
- [Testing](#testing)

## Getting Started

- Clone project
```
git clone https://github.com/jchernandez/posttech.git
```
- Open Android studio
- Build/Run project

## Features

- Retrieve a list of post
- Show the post detail
- Show post comments
- Create a new post

## Prerequisites
- Android Studio Electric Eel | 2022.1.1 Patch 2 or greater

## Dependencies

- Kotlin 1.7.0
- Ktor: 2.3.6
- Room: 2.5.0
- Jetpack Compose: 1.3.2
- Kodein: 7.16.0
- Androidx Lifecycle: 2.6.2
- Android Coroutines: 1.6.0

## Architecture

![alt text](https://github.com/jchernandez/posttech/blob/main/resources/app_arch.png?raw=true)

## Caching

Used Room for caching method so the remote data will be saved locally and if data is not present will retrieve from API. Also made a method to force update the calls, for example when a new Post is created.

## Testing

### Core

Mock Remote and Local clients to test Repository and Models

- Ktor mock
- JUnit
- Kotlinx coroutines
- Mockito kotlin

### App

Mock Repository for test ViewModel
Mock ViewModel for UI test

- JUnit
- Compose ui test
- Mokito android
- Kotlinx coroutines