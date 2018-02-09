//
//  Top50HsTableViewCell.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 2/8/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit

class Top50HsTableViewCell: UITableViewCell {
    @IBOutlet weak var playerName: UILabel!
    @IBOutlet weak var playerPosition: UILabel!
    @IBOutlet weak var playerCommitment: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
