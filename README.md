<h1 align="center">SimpleApiCalls</h1></br>

<p align="center">
:loudspeaker: SimpleApiCalls is a type-safe REST client for Android. The library provides the ability to interact with APIs and send network requests with HttpURLConnection. The advantage of using this library is when you want your application or SDK not to contain many dependencies, you want a smaller size and you want it to be as easy to use as possible. This is a simple example, for more customizations, you can download source code and custom it for your requirements. :tada:
</p><br>

## Including in your project
[![](https://jitpack.io/v/BoyzDroizy/SimpleAPICalls.svg)](https://jitpack.io/#BoyzDroizy/SimpleAPICalls)
<a href="https://github.com/EusebiuCandrea/ToolTipPopupWordTV/blob/master/LICENSE"><img alt="License" src="https://img.shields.io/badge/License-MIT-green.svg"/></a>
</p>

### Gradle
Add below codes to your **root** `build.gradle` file.
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
	        implementation 'com.github.BoyzDroizy:SimpleAPICalls:1.0.0'
	}
```
## Usage

### Basic Example (Kotlin)
Firstly, you need to make the setup for SimpleHttpRequest where you have to add the baseURL<br>

```kotlin
  private val httpRequest: SimpleHttpRequest = SimpleHttpRequest.HTTPRequestBuilder()
    .baseUrl("$baseUrl")
    .build()
```

Here is a basic example with GET method.<br>

```kotlin
  private fun testGetApiCall() {
    val callRequest = Api(
        HTTPMethods.GET,
        "/json/1"
    )

    val requestManager = RequestManager(httpRequest, TestModel::class.java)
    requestManager.request(
        callRequest,
        HttpCallback({ response ->
            binding.responseText.text = "OnSuccess: $response"
        }, { error ->
            binding.responseText.text = "onError: code ${error.code} message ${error.throwable?.message}$"
        })
    )
}
```

POST method.<br>

```kotlin
 private fun testPOSTApiCall() {
    val callRequest = Api(
        HTTPMethods.POST,
        "/json"
    ).apply {
        setParams(TestModel(12345, "abc-def-ghi"))
    }

    val requestManager = RequestManager(httpRequest, Response::class.java)
    requestManager.request(
        callRequest,
        HttpCallback({ response ->
            binding.responseText.text = "OnSuccess: $response"
        }, { error ->
            binding.responseText.text = "onError: code ${error.code} message ${error.throwable?.message}$"
        })
    )
}
```

### `Api class` params:
```kotlin
val httpMethod: HTTPMethods // POST, GET, PUT, DELETE
val url: String
```

### `Api class` methods:
```kotlin
fun <T> setParams(clazz: T) {
    this.params = serialize(clazz)
}

fun setParams(params: JSONObject) {
    this.params = params
}

fun setRequestProperties(requestProperties: HashMap<String, String>) {
    this.requestProperties = requestProperties
}

fun setAuthorization(authorization: String) {
    this.authorization = authorization
}
```

## Find this library useful? :heart:
Be free to use it and enjoy. :star:

# License
```xml
MIT License

    Copyright (c) 2022 BoyzDroizy

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
```
