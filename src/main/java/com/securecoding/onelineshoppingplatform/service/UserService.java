package com.securecoding.onelineshoppingplatform.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.securecoding.onelineshoppingplatform.Model.AuthenticationToken;
import com.securecoding.onelineshoppingplatform.Model.ConfirmationToken;
import com.securecoding.onelineshoppingplatform.Model.Product;
import com.securecoding.onelineshoppingplatform.Model.User;
import com.securecoding.onelineshoppingplatform.exception.AuthenticationFailException;
import com.securecoding.onelineshoppingplatform.exception.CustomException;
//import com.securecoding.onelineshoppingplatform.repository.GetAllProductsRepo;
import com.securecoding.onelineshoppingplatform.repository.ProductRepository;
import com.securecoding.onelineshoppingplatform.repository.TokenRepository;
import com.securecoding.onelineshoppingplatform.repository.UserRepository;
import com.securecoding.onlineshoppingplatform.dto.SignInDto;
import com.securecoding.onlineshoppingplatform.dto.SignResponseDto;
import com.securecoding.onlineshoppingplatform.dto.SignUpDto;
import com.securecoding.onlineshoppingplatform.dto.UserDto;
import com.securecoding.onlineshoppingplatform.dto.UserResponseDto;

@Service
@Component
public class UserService {
	@Autowired
	UserRepository userRepository;

	UserDto userdto;
	@Autowired
	ProductRepository productRepo;
	@Autowired
	AuthenticationService authenticationService;
	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	TokenRepository tokenrepo;
	@Autowired
	ConfirmationTokenService confirmToekService;
	@Autowired
	private com.securecoding.onelineshoppingplatform.util.EmailSender emailSender;

	/*
	 * @Autowired GetAllProductsRepo getAllProducts;
	 */

	@Transactional
	public UserResponseDto signup(SignUpDto signUpDto) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		if (signUpDto.getRole().equalsIgnoreCase("ADMIN")) {
			throw new CustomException("You cannnot be registered as a ADMIN");
		}
		if (Objects.nonNull(userRepository.findByEmail(signUpDto.getEmail()))) {

			throw new CustomException("user already Present");

		}
		System.out.println(signUpDto.getEmail() + "" + signUpDto.getFirstName() + "" + signUpDto.getLastName() + ""
				+ signUpDto.getPassword() + "" + signUpDto.getRole());
		/*
		 * if(signUpDto.getFirstName().isEmpty()||signUpDto.getLastName().isEmpty()||
		 * signUpDto.getPassword().isEmpty()||
		 * signUpDto.getRole().isEmpty()||signUpDto.getRole().equalsIgnoreCase("USER"))
		 * {
		 * 
		 * throw new CustomException("Invalid Details Provided"); }
		 */

		// String encryptedpassword = signUpDto.getPassword();

		/*
		 * try { encryptedpassword = null; } catch (Exception e) { // TODO: handle
		 * exception
		 * 
		 * e.printStackTrace(); }
		 */
		String encryptedpassword = signUpDto.getPassword();

		try {
			encryptedpassword = hashpassword(signUpDto.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		User user = new User(signUpDto.getFirstName(), signUpDto.getLastName(), signUpDto.getEmail(), encryptedpassword,
				signUpDto.getRole());
		// AuthenticationToken authToken = new AuthenticationToken(user);
		// authenticationService.saveToken(authToken);
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15), user);

		String link = "https://localhost:443/user/registration/confirm?token=" + token;
		emailSender.send(signUpDto.getEmail(), buildEmail(signUpDto.getFirstName(), link));
		userRepository.save(user);
		ConfirmationTokenService.saveConfirmationToken(confirmationToken);

		UserResponseDto responseDto = new UserResponseDto("Success", "UserSuccessfully SignedUp");
		return responseDto;

	}

	@Transactional
	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmToekService.getToken(token)
				.orElseThrow(() -> new IllegalStateException("token not found"));

		if (confirmationToken.getConfirmedAt() != null) {
			throw new IllegalStateException("email already confirmed");
		}

		LocalDateTime expiredAt = confirmationToken.getExpiresAt();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expired");
		}

		confirmToekService.setConfirmedAt(token);
		User user = confirmationToken.getAppUser();

		user.setConfirmed(1);
		userRepository.save(user);
		return "Account is confirmed";
	}

	private String buildEmail(String name, String link) {
		return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" + "\n"
				+ "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" + "\n"
				+ "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" + "        \n"
				+ "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
				+ "          <tbody><tr>\n" + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
				+ "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n" + "                    <td style=\"padding-left:10px\">\n"
				+ "                  \n" + "                    </td>\n"
				+ "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
				+ "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n"
				+ "                    </td>\n" + "                  </tr>\n" + "                </tbody></table>\n"
				+ "              </a>\n" + "            </td>\n" + "          </tr>\n" + "        </tbody></table>\n"
				+ "        \n" + "      </td>\n" + "    </tr>\n" + "  </tbody></table>\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
				+ "      <td>\n" + "        \n"
				+ "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n"
				+ "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
				+ "                  </tr>\n" + "                </tbody></table>\n" + "        \n" + "      </td>\n"
				+ "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" + "    </tr>\n"
				+ "  </tbody></table>\n" + "\n" + "\n" + "\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "    <tr>\n"
				+ "      <td width=\"10\" valign=\"middle\"><br></td>\n"
				+ "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
				+ "        \n"
				+ "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name
				+ ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
				+ link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>"
				+ "        \n" + "      </td>\n" + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
				+ "    </tr>\n" + "    <tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n"
				+ "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" + "\n" + "</div></div>";
	}

	private String hashpassword(String password) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return hash;
	}

	public SignResponseDto signin(SignInDto signInDto) {

		// TODO Auto-generated method stub

		User user = userRepository.findByEmail(signInDto.getEmail());
		if (Objects.isNull(user)) {

			throw new AuthenticationFailException("User is not valid,please give a valid email");

		}
		if (user.isConfirmed() == 0) {
			throw new CustomException("User Not Authenticated");
		}

		try {
			if (!user.getPassword().equals(hashpassword(signInDto.getPassword()))) {

				throw new AuthenticationFailException("wrong password");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		User LoggedInuser = userRepository.findByEmail(signInDto.getEmail());

		AuthenticationToken authToekn = tokenrepo.findByUser(LoggedInuser);
		if (Objects.isNull(authToekn)) {
			AuthenticationToken authToken = new AuthenticationToken(user);
			authenticationService.saveToken(authToken);
		}
		// authToekn.
		else {
			
			  AuthenticationToken token = authenticationService.getToken(user);
			  authenticationService.authenticate(token.getToken());
			 
			throw new CustomException("User Already Logged In");
		}

		AuthenticationToken token = authenticationService.getToken(user);
		if (Objects.isNull(token)) {
			throw new CustomException("token is not present");
		}

		return new SignResponseDto("sucess", token.getToken());

	}

	public User editUserDetails(User userfromDB, UserDto user) {
		userfromDB.setFirstName(user.getFirstname());
		userfromDB.setLastName(user.getLastname());
		return userRepository.save(userfromDB);

	}

	public List<Product> getItems(String name) {
		List<Product> productList = productRepo.findByName(name);
		
		if(productList.size()==0) {
			throw new CustomException ("Sorry we don't have"+" "+ name +" in stock");
		}
		return productList;

	}

	public ResponseEntity<SignResponseDto> signout(String token) {
		// authenticate the token
		authenticationService.authenticate(token);

		// find the user

		User user = authenticationService.getUser(token);
		try {
			AuthenticationToken tokens = tokenrepo.findByToken(token);
			System.out.println("*******" + tokens.getToken());
			tokenrepo.delete(tokens);
		} catch (Exception e) {
			throw new CustomException("Token not valid");
		}

		return new ResponseEntity<SignResponseDto>(new SignResponseDto("Success", "Logout Successful"), HttpStatus.OK); // (new
		// ApiResponse(true, "Created a new Category"),HttpStatus.CREATED)
	}

	/*
	 * public List<Product> searchItems(String item) { List<Product>
	 * products=getAllProducts.searchProducts(item); return products; }
	 */

}
