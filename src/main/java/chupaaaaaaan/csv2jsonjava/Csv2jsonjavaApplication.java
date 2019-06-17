package chupaaaaaaan.csv2jsonjava;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Csv2jsonjavaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        // SpringApplication.run(Csv2jsonjavaApplication.class, args);
        SpringApplication.run(Csv2jsonjavaApplication.class, args);
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CsvMapper csvMapper;


    @Override
    public void run(String... args) throws Exception {
        String actionAndMetadata = "{\"index\":{\"_index\":\"test\"}}\n";
        CsvSchema schema = csvMapper.schemaFor(SampleCsv.class);

        try(FileInputStream csvFile = new FileInputStream("test.csv");
            FileOutputStream jsonFile = new FileOutputStream("test.json")) {

            MappingIterator<SampleCsv> rows = csvMapper
                .readerFor(SampleCsv.class)
                .with(schema)
                .readValues(csvFile);

            rows.readAll().stream().forEach(row -> {
                    try {
                        String sourceData = objectMapper.writeValueAsString(row) + "\n";
                        jsonFile.write(actionAndMetadata.getBytes());
                        jsonFile.write(sourceData.getBytes());
                    } catch (JsonProcessingException e) {
                        System.err.println("JsonProcessingError!");
                    } catch (IOException e) {
                        System.err.println("IOExceptionError!");
                    }
                });
            
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }



    // @Override
    // public void run(String... args) throws Exception {
    //     try {
    //         CsvAnnotationBeanReader<SampleCsv> sampleCsvReader =
    //             new CsvAnnotationBeanReader<>(SampleCsv.class,
    //                                           Files.newBufferedReader(Paths.get("test.csv"), Charset.forName("UTF-8")),
    //                                           CsvPreference.STANDARD_PREFERENCE);


    //         List<SampleCsv> sampleCsvlist = sampleCsvReader.readAll();
            
    //         sampleCsvReader.close();

    //         System.out.println(sampleCsvlist);

    //         String json = mapper.writeValueAsString(sampleCsvlist);

    //         System.out.println(json);
            
    //     } catch (IOException e) {
    //         throw new UncheckedIOException(e);
    //     }
        
    // }







}
