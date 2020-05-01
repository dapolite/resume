package com.example.parser.Controller;

import com.example.parser.model.Resume;
import com.example.parser.parser.impl.ResumeParser;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
public class ParseController {
        @RequestMapping(value = "/upload",
                method = RequestMethod.POST,
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

        /*public static void demo() {
            try {
                ResumeParser resumeParser = new ResumeParser();
                String resume = resumeParser.parse(new File("sample_cv_en.pdf"));
                System.out.println(resume);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
*/
        public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException
        {
            File convertFile = new File("D:/work/" + file.getOriginalFilename());
            convertFile.createNewFile();

            try (FileOutputStream fout = new FileOutputStream(convertFile))
            {
                fout.write(file.getBytes());
            }
            catch (Exception exe)
            {
                exe.printStackTrace();
            }

            try {
                ResumeParser resumeParser = new ResumeParser();
                File pathIn = new File(convertFile.toString());
                File pathOut = new File("D:/work/data.json");
                if (pathOut.exists()) {
                    System.err.println("Output path is invalid!");
                }
                if (pathIn.isFile()) {
                    System.out.println("Parsing...");

                    long startTime = System.currentTimeMillis();

                    String resume = resumeParser.parse(pathIn);
                    FileUtils.writeStringToFile(pathOut, resume, UTF_8);

                    long endTime = System.currentTimeMillis();

                    System.out.println(String.format("Done! (%d milliseconds)", endTime - startTime));
                } else if (pathIn.isDirectory()) {
                    System.out.println("Preparing...");

                    File[] files = pathIn.listFiles();
                    Map<String, Resume> folderMap = new HashMap<>();
                    Gson gson = new Gson();

                    long startTime = System.currentTimeMillis();

                    for (File file1 : files) {
                        System.out.println(String.format("Parsing: %s", file1.getName()));
                        String resume = resumeParser.parse(file1);
                        folderMap.put(file1.getName(), gson.fromJson(resume, Resume.class));
                    }

                    FileUtils.writeStringToFile(pathOut, new Gson().toJson(folderMap).replace("\\u0026", "&"), UTF_8);

                    long endTime = System.currentTimeMillis();

                    System.out.println(String.format("Done! (%d milliseconds)", endTime - startTime));
                } else {
                    System.err.println("Input path not found!");
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

            return "File has uploaded successfully";
        }

}

