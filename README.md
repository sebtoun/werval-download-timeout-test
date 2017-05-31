# Werval long download read timeout test

This project illustrates the read timeout triggered during long downloads bug in werval

## Details

There is one single route `/` that downloads 64 Mo of random bytes.
Netty read timeout is set to 10 seconds in dev mode.
If the client does not read the whole response data before the 10 seconds, the connection is abruptly closed and the client gets a broken connection.

To run the server in development mode: `gradle devshell`

To run the client script, yout will need python2.7 and requests module installed:

```
pip install -r pythonScript/requirements.txt
python pythonScript/werval_dl_test.py
```

a typical output will look like:

```
> python pythonScript/werval_dl_test.py
send: 'GET / HTTP/1.1\r\nHost: 127.0.0.1:23023\r\nConnection: keep-alive\r\nAccept-Encoding: gzip, deflate\r\nAccept: */*\r\nUser-Agent: python-requests/2.17.3\r\n\r\n'
reply: 'HTTP/1.1 200 OK\r\n'
header: Connection: keep-alive
header: Content-Type: application/octet-stream
header: X-Werval-Request-ID: 9fbbb151-9e0f-4eee-b9d5-37392c59e2b7_0
header: Transfer-Encoding: chunked
header: Trailer: X-Werval-Content-Length
total downloaded: 9666560


Elapsed: 12.2130000591
Traceback (most recent call last):
  File "pythonScript/werval_dl_test.py", line 61, in <module>
    for chunk in r.iter_content(None):
  File "C:\Python27\lib\site-packages\requests\models.py", line 742, in generate
    raise ChunkedEncodingError(e)
requests.exceptions.ChunkedEncodingError: ('Connection broken: IncompleteRead(0 bytes read)', IncompleteRead(0 bytes read))
```