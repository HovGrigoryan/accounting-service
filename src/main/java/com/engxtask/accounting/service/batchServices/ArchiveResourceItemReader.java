package com.engxtask.accounting.service.batchServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
public class ArchiveResourceItemReader<T> extends MultiResourceItemReader<T> {

    private ZipFile zipFile;

    private static  final int BUFFER_SIZE = 8192;

    private ZipInputStream zipInputStream;
    private Resource resource;
    private static final Logger LOG = LoggerFactory.getLogger(ArchiveResourceItemReader.class);

//    @Override
//    public void close() throws ItemStreamException {
//        try {
//            if (zipInputStream != null) {
//                zipInputStream.close();
//            }
//        } catch (IOException ex) {
//            LOG.error("Error during closing the archive file: {}", ex.getMessage());
//            throw new ItemStreamException(ex);
//        } finally {
//            if (zipInputStream != null) {
//                try {
//                    zipInputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    @Override
    public void open(@NonNull ExecutionContext context) {
        if (resource != null) {
            try {
                zipInputStream = new ZipInputStream(resource.getInputStream());
//                zipFile = new ZipFile(resource.getFile());
                Resource[] resources = extractFiles(zipInputStream);
                this.setResources(resources);
            } catch (IOException ex) {
                LOG.error("Error reading archive file {}: Error message: {}",
                        resource.getFilename(), ex.getMessage());
            }
        } else {
            LOG.warn("Cannot open the resource");
            return;
        }
        super.open(context);
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    private Resource[] extractFiles(final ZipInputStream zipInputStream) throws IOException {
        ZipEntry entry;
        List<Resource> resources = new ArrayList<>();
        while ((entry = zipInputStream.getNextEntry()) != null) {
            if (!entry.isDirectory()) {

                byte[] bytes = extractEntry(entry, zipInputStream);

                ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);

                resources.add(byteArrayResource);
            }
        }
        return resources.toArray(new Resource[0]);
//        return zipFile.stream()
//                .filter(zipEntry -> !zipEntry.isDirectory())
//                .map(zipEntry -> {
//                    try {
//                        return new InputStreamResource(
//                                zipFile.getInputStream(zipEntry),
//                                zipEntry.getName()
//                        );
//                    } catch (IOException e) {
//                        LOG.error("Error reading archive file {}: Error message: {}",
//                                e.getMessage(), zipEntry.getName());
//                        return null;
//                    }
//                })
//                .toArray(value -> new Resource[value]);
    }

    public static byte[] extractEntry(final ZipEntry entry, InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream((int) entry.getSize());
        try {
            final byte[] buf = new byte[BUFFER_SIZE];
            int length;
            while ((length = is.read(buf, 0, buf.length)) >= 0) {
                baos.write(buf, 0, length);
            }
        } catch (IOException ioex) {
            baos.close();
        }
        return baos.toByteArray();
    }
}
