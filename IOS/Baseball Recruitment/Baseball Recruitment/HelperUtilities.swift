//
//  HelperUtilities.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 2/28/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit

class DateTime
{
    static func getDateString(_ format: String) -> String
    {
        // Formats:
        //  ""yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        let date = Date()
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = format
        
        return dateFormatter.string(from: date)
    }
}
