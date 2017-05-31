# -*- coding: utf-8 -*-
"""
Created on Wed May 31 17:20:51 2017

@author: seb
"""

import requests
import time

# enable verbose requests logging
try:
    import http.client as http_client
except ImportError:
   # Python 2
    import httplib as http_client
http_client.HTTPConnection.debuglevel = 1

HOST = "http://127.0.0.1:23023"
DL_URL = "/"

TIMEOUT = None

CONNECTION_HEADER = "keep-alive"
# CONNECTION_HEADER = "close"

r = requests.request(method="GET",
                     url=HOST + DL_URL,
                     headers={
                         "Connection": CONNECTION_HEADER
                     },
                     timeout=TIMEOUT,
                     stream=True)
r.raise_for_status()

tstart = time.time()
total = 0

try:
    for chunk in r.iter_content(None):
        total += len(chunk)
        # simulate slow download
        time.sleep(0.01)
finally:
    print "\n"
    print "Downloaded: %s should be 64Mo(%s)" % (total, 1024 * 1024 * 64)
    print 'Elapsed: %s seconds' % (time.time() - tstart)
    print "\n"
