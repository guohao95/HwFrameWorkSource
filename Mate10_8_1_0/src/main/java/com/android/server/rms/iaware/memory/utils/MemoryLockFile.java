package com.android.server.rms.iaware.memory.utils;

import android.rms.iaware.AwareLog;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import java.io.File;
import java.io.FileDescriptor;
import java.util.ArrayList;

public class MemoryLockFile {
    private static final String TAG = "AwareMem_MemLockFile";
    private final ArrayList<PinnedFile> mPinnedFiles = new ArrayList();

    private static class PinnedFile {
        long mAddress;
        String mFilename;
        long mLength;

        PinnedFile(long address, long length, String filename) {
            this.mAddress = address;
            this.mLength = length;
            this.mFilename = normalize(filename);
        }

        private String normalize(String path) {
            return new File(path.trim()).getName();
        }
    }

    private PinnedFile pinFile(String fileToPin, long offset, long length, long maxSize) {
        FileDescriptor fd = new FileDescriptor();
        try {
            fd = Os.open(fileToPin, (OsConstants.O_RDONLY | OsConstants.O_CLOEXEC) | OsConstants.O_NOFOLLOW, OsConstants.O_RDONLY);
            StructStat sb = Os.fstat(fd);
            if (offset + length > sb.st_size) {
                Os.close(fd);
                AwareLog.e(TAG, "Failed to pin file " + fileToPin + ", request extends beyond end of file.  offset + length =  " + (offset + length) + ", file length = " + sb.st_size);
                return null;
            }
            if (length == 0) {
                length = sb.st_size - offset;
            }
            if (maxSize <= 0 || length <= maxSize) {
                long address = Os.mmap(0, length, OsConstants.PROT_READ, OsConstants.MAP_PRIVATE, fd, offset);
                Os.close(fd);
                Os.mlock(address, length);
                return new PinnedFile(address, length, fileToPin);
            }
            AwareLog.e(TAG, "Could not pin file " + fileToPin + ", size = " + length + ", maxSize = " + maxSize);
            Os.close(fd);
            return null;
        } catch (ErrnoException e) {
            AwareLog.e(TAG, "Could not pin file " + fileToPin + " with error " + e.getMessage());
            if (fd.valid()) {
                try {
                    Os.close(fd);
                } catch (ErrnoException eClose) {
                    AwareLog.e(TAG, "Failed to close fd, error = " + eClose.getMessage());
                }
            }
            return null;
        }
    }

    private void unpinFile(PinnedFile pf) {
        try {
            Os.munlock(pf.mAddress, pf.mLength);
        } catch (ErrnoException e) {
            AwareLog.e(TAG, "Failed to unpin file with error " + e.getMessage());
        }
    }

    public void iAwareAddPinFile() {
        ArrayList<String> filesToPin = MemoryConstant.getFilesToPin();
        int fileNumSize = filesToPin.size();
        for (int i = 0; i < fileNumSize; i++) {
            PinnedFile pf = pinFile((String) filesToPin.get(i), 0, 0, 0);
            if (pf != null) {
                this.mPinnedFiles.add(pf);
                AwareLog.d(TAG, "Pinned file " + pf.mFilename + "ok");
            } else {
                AwareLog.e(TAG, "Failed to pin file");
            }
        }
    }

    public void clearPinFile() {
        int pinnedFileNum = this.mPinnedFiles.size();
        for (int i = 0; i < pinnedFileNum; i++) {
            unpinFile((PinnedFile) this.mPinnedFiles.get(i));
        }
        this.mPinnedFiles.clear();
    }
}
