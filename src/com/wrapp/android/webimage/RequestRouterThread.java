/*
 * Copyright (c) 2012 Bohemian Wrappsody AB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.wrapp.android.webimage;

import android.graphics.Bitmap;

public class RequestRouterThread extends TaskQueueThread {
  protected static RequestRouterThread staticInstance;

  public static RequestRouterThread getInstance() {
    if(staticInstance == null) {
      staticInstance = new RequestRouterThread();
    }
    return staticInstance;
  }

  private RequestRouterThread() {
    super("RequestRouter");
    setPriority(Thread.NORM_PRIORITY - 1);
  }

  @Override
  protected Bitmap processRequest(ImageRequest request) {
    if(ImageCache.isImageCached(request.context, request.imageKey)) {
      FileLoaderThread.getInstance().addTask(request);
    }
    else {
      DownloadThreadPool.getInstance().addTask(request);
    }

    return null;
  }

  @Override
  protected void onRequestComplete(RequestResponse response) {
    // Never reached
  }

  @Override
  protected void onRequestCancelled(ImageRequest request) {
    // Nothing to do
  }
}
