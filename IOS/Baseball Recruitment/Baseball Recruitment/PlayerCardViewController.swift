//
//  PlayerCardViewController.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 2/9/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit

class PlayerCardViewController: UIViewController {
    //MARK: Properties
    var hrefPath : String?
    var playerCard = PlayerCard()
    
    @IBOutlet weak var playerImage: UIImageView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var hsGradLabel: UILabel!
    @IBOutlet weak var currentAgeLabel: UILabel!
    @IBOutlet weak var ageAtDraftLabel: UILabel!
    
    //MARK: Override functions
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        if let href = hrefPath {
            playerCard.requestPlayerCard(hrefPath: href) {
                let imagePath = self.playerCard.imagePath
                if !imagePath.isEmpty {
                    self.playerCard.requestPlayerImage(url: imagePath) {
                        DispatchQueue.main.async {
                            self.playerImage.image = self.playerCard.image
                        }
                    }
                }
                
                self.nameLabel.text = "Name: \(self.playerCard.name)"
                self.hsGradLabel.text = "HS Grad: \(self.playerCard.hsGrad)"
                self.currentAgeLabel.text = "Current Age: \(self.playerCard.age)"
                self.ageAtDraftLabel.text = "Age at Draft: \(self.playerCard.ageAtDraft)"
            }
        }
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    
    // MARK: - Navigation
    /*
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
