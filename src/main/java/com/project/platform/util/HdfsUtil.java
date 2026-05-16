package com.project.platform.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HdfsUtil {

    @Autowired
    private Configuration configuration;

    public FileSystem getFileSystem() throws IOException {
        return FileSystem.get(configuration);
    }

    public boolean mkdir(String path) throws IOException {
        try (FileSystem fs = getFileSystem()) {
            boolean result = fs.mkdirs(new Path(path));
            return result;
        }
    }

    public boolean updateFile(String localPath, String hdfsPath) throws IOException {
        try (FileSystem fs = getFileSystem()) {
            fs.copyFromLocalFile(new Path(localPath), new Path(hdfsPath));
            return true;
        }
    }

    public boolean downloadFile(String hdfsPath, String localPath) throws IOException {
        try (FileSystem fs = getFileSystem()) {
            fs.copyToLocalFile(new Path(hdfsPath), new Path(localPath));
            return true;
        }
    }

    public boolean deleteFile(String hdfsPath, boolean recursive) throws IOException {
        try (FileSystem fs = getFileSystem()) {
            boolean result = fs.delete(new Path(hdfsPath), recursive);
            return result;
        }
    }

    public List<LocatedFileStatus> listFiles(String hdfsPath) throws IOException {
        FileSystem fs = getFileSystem();
        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(new Path(hdfsPath), true);
        List<LocatedFileStatus> fileList = new ArrayList<>();
        while (iterator.hasNext()) {
            fileList.add(iterator.next());
        }
        return fileList;
    }

}
