//
//  TournamentTableViewCell.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 3/3/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit

class TournamentTableViewCell: UITableViewCell {
    @IBOutlet weak var tournamentLabel: UILabel!
    @IBOutlet weak var locationLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
