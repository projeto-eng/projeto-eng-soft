package br.usp.ime.projetoengsoft.controller.api;


import br.usp.ime.projetoengsoft.dto.UserDto;
import br.usp.ime.projetoengsoft.model.User;
import br.usp.ime.projetoengsoft.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class LoginApi {
    @Autowired
    private LoginService loginService;

    @GetMapping("/get-user")
    public ResponseEntity<User> getUser(@RequestParam String user) {
        if (user == null || user.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User onDb = loginService.getUser(user);

        if (onDb == null)  return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (!onDb.getUser().equals(user))  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(onDb, HttpStatus.OK);
    }

    @GetMapping("/check-pass")
    public ResponseEntity<String> checkPass(@RequestParam String user, @RequestParam String pass) {
        if (user == null || pass == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User onDb = loginService.getUser(user);

        if (onDb == null) return new ResponseEntity<>("USER NOT FOUND",HttpStatus.BAD_REQUEST);

        if (onDb.getUser().equals(user) && onDb.getPass().equals(pass)) return new ResponseEntity<>("OK", HttpStatus.OK);

        return new ResponseEntity<>("NO MATCH", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody UserDto newUser) {
        if (newUser == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        User onDb = loginService.getUser(newUser.getUser());

        if (onDb != null) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        User toCreate = new User(newUser);

        String response = loginService.criaUser(toCreate);

        if (response.equals("OK")) return  new ResponseEntity<>(loginService.getUser(newUser.getUser()), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-user")
    public ResponseEntity<User> updateUser(@RequestBody UserDto newUser) {
        if (newUser == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        User toUpdate = new User(newUser);

        String response = loginService.updateUser(toUpdate);

        if (response.equals("OK")) return new ResponseEntity<>(loginService.getUser(newUser.getUser()), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestParam String user, @RequestParam String pass) {
        if (user == null || pass == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User onDb = loginService.getUser(user);

        if (onDb == null) return new ResponseEntity<>("USER NOT FOUND",HttpStatus.BAD_REQUEST);

        if (!onDb.getUser().equals(user) || !onDb.getPass().equals(pass)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        loginService.deleteUser(onDb);

        if (loginService.getUser(user) == null) return new ResponseEntity<>("OK", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
