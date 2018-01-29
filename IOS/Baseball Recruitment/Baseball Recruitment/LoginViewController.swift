//
//  LoginViewController.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 1/28/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit
import os.log

class LoginViewController: UIViewController {
    //MARK: Properties
    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    
    //MARK: Actions
    @IBAction func loginPressed(_ sender: Any) {
        let username = usernameTextField.text ?? ""
        let password = passwordTextField.text ?? ""
        
        os_log("Logging in (username,password)=(%@,%@)",
            log: OSLog.default, type: .debug, username, password)
        
        performSegue(withIdentifier: "LoginPressed", sender: self)
    }
    
    //MARK: ViewController Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

