//
//  PlayerSearch.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 2/17/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit
import Kanna
import Alamofire

class PlayerSearch
{
    func requestSearch(search: String, completion: @escaping ([PlayerCard]?) -> ()) {
        let mainUrl = "https://www.perfectgame.org/Search.aspx?search="
        let subUrl = search.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
        let url = mainUrl + subUrl
        
        Alamofire.request(url).responseString{ response in
            guard response.result.isSuccess else {
                print("Alamofire.request failed.\n\(String(describing: response.error))")
                return
            }
            
            if let html = response.result.value {
                completion(self.scrapeSearch(html: html))
            }
        }
    }
    
    func scrapeSearch(html: String) -> [PlayerCard]? {
        guard let doc = try? Kanna.HTML(html: html, encoding: String.Encoding.utf8)
            else {
                print("Kana.HTML failed.")
                return nil
        }
        
        var selector = "#ContentPlaceHolder1_gvPlayerSearch"
        guard let searchTable = doc.at_css(selector) else {
            return nil
        }
        
        selector = "tr"
        let rows = searchTable.css(selector)
        guard rows.count > 0 else {
            return nil
        }
        
        var players = [PlayerCard]()
        
        for row in rows {
            let player = PlayerCard()
            
            // Skip rows with table header
            selector = "th"
            let tableHeaders = row.css(selector)
            if tableHeaders.count > 0 {
                continue
            }
            
            // Player name and href
            selector = "a[id^='ContentPlaceHolder1_gvPlayerSearch_hlPlayerName']"
            guard let aNode = row.at_css(selector) else {
                continue
            }
            
            player.name = aNode.text ?? ""
            player.hrefPath = aNode["href"] ?? ""
            
            if player.name.isEmpty || player.hrefPath.isEmpty {
                continue
            }
            
            // Player position
            let positionNode = row.at_css("td:nth-child(2)")
            player.position = positionNode?.text ?? ""
            
            // High school graduation year
            let hsGradNode = row.at_css("td:nth-child(3)")
            player.hsGrad = hsGradNode?.text ?? ""
            
            // Home town
            selector = "span[id^='ContentPlaceHolder1_gvPlayerSearch_lblHomeTown']"
            let homeTownNode = row.at_css(selector)
            player.homeTown = homeTownNode?.text ?? ""
            
            players.append(player)
        }
        
        return (players.isEmpty) ? nil : players
    }
}
