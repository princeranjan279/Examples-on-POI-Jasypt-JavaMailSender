package com.example.poi.serviceimpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.poi.entity.MessageData;
import com.example.poi.entity.User;
import com.example.poi.repository.UserRepository;
import com.example.poi.service.UserService;
import com.example.poi.util.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private JavaMailSender javaMailSender;

	StringEncryptor encryptor;

	public UserServiceImpl(StringEncryptor stringEncryptor) {
		this.encryptor = stringEncryptor;
	}

	public UserServiceImpl() {

	}

	@Override
	public ResponseEntity<ResponseStructure<User>> saveUser(User user) {
		user = repository.save(user);
		ResponseStructure<User> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("saved!!");
		structure.setData(user);
		return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<List<User>>> extractUsersFromExcel(MultipartFile file) throws IOException {
		List<User> users = new ArrayList<>();
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

		for (Sheet sheet : workbook) {
			for (Row row : sheet) {
				if (row.getRowNum() > 0) {
					if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null) {
						System.err.println(row.getRowNum());
						continue;
					} else {
					System.out.println(row.getRowNum());
						User user = new User();
						user.setUserName(row.getCell(1).getStringCellValue());
//						String encryptedEmail = encryptor.encrypt(row.getCell(2).getStringCellValue());
						user.setUserEmail(row.getCell(2).getStringCellValue());
						user.setUserPhNo(9876567573l);
						repository.save(user);
						users.add(user);
						System.out.println(user);
					}
				}
			}
		}
		ResponseStructure<List<User>> structure = new ResponseStructure<>();
		structure.setData(users);
		structure.setMessage("Users data saved successfully!!");
		structure.setStatus(HttpStatus.OK.value());

		workbook.close();
		return new ResponseEntity<ResponseStructure<List<User>>>(structure, HttpStatus.OK);
	}

	static {

	}

	@Override
	public ResponseEntity<ResponseStructure<List<User>>> getUsers() {
		System.err.println("-----------------------------------------------The current Thread is "+ Thread.currentThread().getName());
		List<User> users = repository.findAll();
		if (users != null) {
			ResponseStructure<List<User>> structure = new ResponseStructure<>();
//			users.stream().forEach((user) -> {
//				user.setUserEmail(encryptor.decrypt(user.getUserEmail()));
//			});
			
			structure.setData(users);
			structure.setMessage("Found all users!");
			structure.setStatus(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<User>>>(structure, HttpStatus.FOUND);
		} else {
			return null;
		}
	}

	@Override
	public ResponseEntity<String> uploadUsersToExcel(String filePath) throws IOException {

		List<User> users = repository.findAll();

		if (!users.isEmpty()) {
			// Create a new workbook
			XSSFWorkbook workbook = new XSSFWorkbook();

			// Create a new sheet
			Sheet sheet = workbook.createSheet();

			// Create headers
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("Name");
			headerRow.createCell(2).setCellValue("Email");

			// Populate rows with user data
			int rowNum = 1;
			for (User user : users) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(user.getUserId());
				row.createCell(1).setCellValue(user.getUserName());
				row.createCell(2).setCellValue(user.getUserEmail());
			}

			// Save the workbook to a file
			FileOutputStream fileOut = new FileOutputStream(filePath);
			workbook.write(fileOut);

			// Close the workbook
			workbook.close();
			return new ResponseEntity<String>("Users data uploaded successfully!!", HttpStatus.OK);
		} else
			return null;

	}

	public String example(String filePath) throws IOException {
		List<User> users = repository.findAll();
		if (!users.isEmpty()) {
			// create a workbook
			XSSFWorkbook workbook = new XSSFWorkbook();

			// create a sheet in the workbook
			Sheet sheet = workbook.createSheet();

			// create a header row for the worksheet
			Row headers = sheet.createRow(0);
			headers.createCell(0).setCellValue("UserId");
			headers.createCell(1).setCellValue("UserName");
			headers.createCell(2).setCellValue("UserEmail");
			headers.createCell(3).setCellValue("PhoneNumber");

			// populate the user data for the sheet corresponding to the respective headers
			int rowNum = 0;
			for (User user : users) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(user.getUserId());
				row.createCell(1).setCellValue(user.getUserName());
				row.createCell(2).setCellValue(user.getUserEmail());
				row.createCell(3).setCellValue(user.getUserPhNo());
			}

			// create a fileOutPutStream
			FileOutputStream outputStream = new FileOutputStream(filePath);
			workbook.write(outputStream);
			workbook.close();
		}
		System.out.println("data saved!!");
		return "Data successfully daved";
	}

	@Override
	public ResponseEntity<String> readCSV(MultipartFile file) throws IOException {
		InputStream inputStream = file.getInputStream();
		Reader reader = new InputStreamReader(inputStream);
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
		List<CSVRecord> records = csvParser.getRecords();

		for (CSVRecord record : records) {
			User user = new User();
			user.setUserName(record.get(1));
			user.setUserEmail(record.get(2));
			user.setUserPhNo(9876785622l);
			repository.save(user);
		}
		csvParser.close();
		
		return new ResponseEntity<String>("Data transfered for database", HttpStatus.OK);
	}

	public ResponseEntity<String> writeUsersToCSV(String filePath) throws IOException {
		List<User> users = repository.findAll(); // Fetch users from the database

		FileWriter fileWriter = new FileWriter(filePath);
		CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
		for (User user : users) {
			System.out.println(user);
			csvPrinter.printRecord(user.getUserId(), user.getUserName(), user.getUserEmail(), user.getUserPhNo());
		}
		csvPrinter.flush();
		System.out.println(csvPrinter);
		csvPrinter.close();
		return new ResponseEntity<String>("Data transfered to CSV file!!", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> sendMailToStudents(MessageData messageData) {
		SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(messageData.getTo());
				message.setSubject(messageData.getSubject());
				message.setText(
						
						messageData.getText()
						+"\n\n"+messageData.getSenderName()+",\n"+messageData.getSenderAddress()
						
						);
				message.setSentDate(new Date());
				javaMailSender.send(message);
				
		return new ResponseEntity<String>("Mails sent successfully!!", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> sendMimeMail(MessageData data) throws MessagingException{
//		byte[] filedata = file.getBytes();
		
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(new InternetAddress("rajugowda0212@gmail.com"));
			helper.setTo(data.getTo());
			helper.setSubject(data.getSubject());
			helper.setSentDate(new Date());
			String emailBody = data.getText()
					+"<br><br><h4>Thanks & Regards</h4>"
					+"<h4>"+data.getSenderName()+",<br>"
					+data.getSenderAddress()+"</h4>"
					+"<img src=\"https://www.testyantra.com/sites/default/files/tylog1.png\" width=\"250\">";
			helper.setText(emailBody, true);
			javaMailSender.send(mimeMessage);
		}catch(Exception e) {
			return new ResponseEntity<String>("Incorrect email address!!", HttpStatus.BAD_REQUEST);
		}
		
//		helper.addInline("Logo", new ByteArrayResource(filedata), "image/png");
		return new ResponseEntity<String>("Mails sent successfully!!", HttpStatus.OK);

	}

	@Override
	@Async
	public ResponseEntity<String> testAsyncBehaviour() {
		LocalTime startTime = LocalTime.now();
		System.err.println("\nMethod started at "+startTime+"With Thread "+Thread.currentThread().getName()+"\n");
		
		List<String> users = Arrays.asList("Ashok","Vinu","Pooja","Pavan","Shruthi","Aman","Kamal","Sumanth");
		for(String user : users) {
			sendMail(user);
		}
		LocalTime endTime = LocalTime.now();
		System.err.println("\nMehtod ended at "+endTime+"With Thread "+Thread.currentThread().getName()+"\n");
		return new ResponseEntity<String>("thread started at "+startTime+" ended at "+endTime, HttpStatus.OK);
	}
	
	public void sendMail(String user) {
		
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Mail sent to "+user+" using Thread ------------------- "+Thread.currentThread().getName());
		
 		
	}

}
