# Blog

## Usage

```sh
gcloud auth application-default login \
    --scopes='https://www.googleapis.com/auth/blogger' \
    --client-id-file __credentials-installed.json
export GOOGLE_APPLICATION_CREDENTIALS=~/.config/gcloud/application_default_credentials.json
```

Then

```sh
lein run /path/to/article.md
```

## References

* [Blogger API: Getting Started](https://developers.google.com/blogger/docs/3.0/getting_started)
* [Blogger APIs Client Library for Java](https://developers.google.com/blogger/docs/3.0/api-lib/java)
* [google-api-java-client-services / clients / google-api-services-blogger](https://github.com/googleapis/google-api-java-client-services/tree/1611d9bbe954dba900337a82cd8d081cbc7cd47f/clients/google-api-services-blogger)
* [googleapis / google-auth-library-java](https://github.com/googleapis/google-auth-library-java)
