
# Use Blogger API from its Java client

Google Blogger provides an API to create and edit blog posts on Blogger. See their [Introduction][1] here.

It also provides [API client libraries][2] for various languages including Java, JS, Python, etc.

As I haven't found a sample app for the Java client, I'm writing this with the hope that this can save someone's time.

In this post, I'm going to create a new blog post with a title **"My Test Post"**.

## Prerequisite

* You have a blog on Blogger (in this post, it's `https://blog.example.org`)
* You have a GCP project with Billing enabled.
* You have `gcloud` command installed. cf. [here][3].

## Steps

1. Enable Blogger API
1. Create OAuth 2.0 credentials
1. Create OAuth consent screen
1. Create a Java app that update your blog
1. Get a new OAuth token
1. Run the Java app to create a new post on your blog

### Step 1. Enable Blogger API

Go to Blogger API page, make sure the correct project is selected, then enable the API.

https://console.cloud.google.com/apis/api/blogger.googleapis.com/overview

### Step 2. Create OAuth 2.0 credentials

1. Go to API credentials page, make sure the correct project is selected

    https://console.developers.google.com/apis/credentials

1. Create a new OAuth client ID, with "Application type" **Other**.
1. Download the credentials of the newly created item, confirm that the credential
   contains the following information:

    ```json
    {
      "installed": {
        "client_id": "...",
        "client_secret": "...",
        ...
      }
    }
    ```

### Step 3. Create OAuth consent screen

1. Go to: https://console.developers.google.com/apis/credentials/consent
1. Make sure you have valid "Support email" is entered.
1. Add `../auth/blogger` scope to allow managing your blogger account.
1. "Save"

### Step 4. Create a Java app that update your blog

```java
// src/main/java/org/example/App.java
package org.example;

import com.google.api.services.blogger.Blogger;
import com.google.api.services.blogger.BloggerRequestInitializer;
import com.google.api.services.blogger.model.Blog;
import com.google.api.services.blogger.model.Post;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class App {
    public static void main(String[] argv) throws GeneralSecurityException, IOException {
        Blogger blogger = getBlogger();

        Post newPost = new Post().setTitle("My Test Post").setContent("With <b>exciting</b> content...");

        Blog blog = blogger.blogs().getByUrl("https://blog.example.org/").execute();
        Blogger.Posts.Insert command = blogger.posts().insert(blog.getId(), newPost);

        System.out.println(command.executeUnparsed().parseAsString());
    }

    private static Blogger getBlogger() throws GeneralSecurityException, IOException {
        Blogger.Builder builder = new Blogger.Builder(
            com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport(),
            new com.google.api.client.json.jackson2.JacksonFactory(),
            new HttpCredentialsAdapter(GoogleCredentials.getApplicationDefault())
        );
        return builder.setBloggerRequestInitializer(new BloggerRequestInitializer()).build();
    }
}
```

```gradle
// build.gradle

plugins {
    id 'java'
    id 'application'
}

group 'org.example'
version '1.0-SNAPSHOT'

sourceCompatibility = 1.8

repositories {
    mavenCentral()
}

dependencies {
    compile 'com.google.apis:google-api-services-blogger:v3-rev20190917-1.30.9'
    compile 'com.google.auth:google-auth-library-oauth2-http:0.20.0'
}

mainClassName = 'org.example.App'
```

### Step 5. Get a new OAuth token

Execute the following command. It will seek your approval on a browser
unless you use `--no-launch-browser` option.

```sh
gcloud auth application-default login \
    --scopes='https://www.googleapis.com/auth/blogger' \
    --client-id-file downloaded-oauth-client-id.json
```

This will save an Application Default Credentials (ADC) at `$HOME/.config/gcloud/application_default_credentials.json`.

### Step 6. Run the Java app to create a new post on your blog

Make the credential available to the app through an environment variable, and run the app.

```sh
export GOOGLE_APPLICATION_CREDENTIALS=~/.config/gcloud/application_default_credentials.json
gradle run
```

Now open your blog and confirm the new post is published.


[1]: https://developers.google.com/blogger
[2]: https://developers.google.com/blogger/docs/3.0/libraries#libraries
[3]: https://cloud.google.com/sdk/install

<!-- POST_ID: b7308ec2-e845-4641-9d64-7e16daf42e97 -->
