package com.badlogic.androidgames.framework.FILEIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by dell on 7/30/2016.
 */
public interface FileIO {
    public InputStream readAsset(String fileName) throws IOException;
    public InputStream readFile(String fileName) throws IOException;
    public OutputStream writeFile(String fileName) throws IOException;
}
