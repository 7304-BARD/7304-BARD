//
//  Top50HighSchool.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 2/7/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit
import Kanna
import Alamofire

class Top50HighSchool
{
    struct Player
    {
        // These are the values that are scraped from the top 50 screen.
        var name : String
        var position : String
        var href: String
        var commitment : String?
    }
    
    //MARK: Get Valid 'Class of' year labels.
    func requestValidYears(completion : @escaping ([String]?) -> ()) {
        let date = Date()
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy"
        let year = dateFormatter.string(from: date)
        
        let mainUrl = "https://www.perfectgame.org/"
        let subUrl = "Rankings/Players/NationalRankings.aspx?gyear=\(year)"
        let url = mainUrl + subUrl
        
        Alamofire.request(url).responseString{ response in
            guard response.result.isSuccess else {
                print("Alamofire.request failed.\n\(String(describing: response.error))")
                return
            }
            
            if let html = response.result.value {
                completion(self.scrapeValidYears(html: html))
            }
        }
    }
    
    func scrapeValidYears(html: String) -> [String] {
        var validYears = [String]()
        
        guard let doc = Kanna.HTML(html: html, encoding: String.Encoding.utf8)
            else {
                print("Kana.HTML failed.")
                return validYears
        }
        
        let selector = "#ctl00_ContentPlaceHolder1_RadComboBox1_DropDown"
        let nodes = doc.css(selector)
        
        if nodes.count < 1 {
            return validYears
        }
        
        let node = nodes[0]
        let liNodes = node.css("li")
        
        
        for liNode in liNodes {
            validYears.append(liNode.text ?? "")
            //print(liNode.text ?? "")
        }
        
        return validYears
    }
    
    //MARK: Get top 50 students for a particular year
    func requestTopHighSchool(year: Int = 2020, completion : @escaping (Int, [Player]) -> ()) {
        let mainUrl = "https://www.perfectgame.org/"
        let subUrl = "Rankings/Players/NationalRankings.aspx?gyear=\(year)"
        let url = mainUrl + subUrl
        Alamofire.request(url).responseString{ response in
            guard response.result.isSuccess else {
                print("Alamofire.request failed.\n\(String(describing: response.error))")
                return
            }
            
            if let html = response.result.value {
                let players = self.scrapeTopHighSchool(year: year, html: html)
                completion(year, players)
            }
        }
    }
    
    func scrapeTopHighSchool(year: Int, html: String) -> [Player] {
        var players = [Player]()
        
        guard let doc = Kanna.HTML(html: html, encoding: String.Encoding.utf8)
            else {
                print("Kana.HTML failed.")
                return players
        }
        
        let tableSelector = "table[id='ContentPlaceHolder1_gvPlayers']"
        let tables = doc.css(tableSelector)
        
        guard tables.count > 0 else {
            return players
        }
        
        let table = tables[0]
        let rows = table.css("tr")
        let nRows = rows.count
        
        guard nRows > 0 else {
            return players
        }
        
        for i in 1..<nRows {
            if i % 2 == 0 {
                continue
            }
            
            let row = rows[i]
            
            let tableHeaderNodes = row.css("th")
            if tableHeaderNodes.count > 0 {
                // Skip the table row with table headers.
                continue
            }
            
            let multiPageNode = row.at_css("a[href*='Page$']")
            if multiPageNode != nil {
                // Skip the table row with page request information.
                continue
            }
            
            let aNode1 = row.at_css("a[href*='Players/Playerprofile.aspx?ID=']")
            let href = aNode1?["href"] ?? ""
            let playerName = aNode1?.text ?? ""
            
            let tdNode = row.at_css("td:nth-child(3)")
            let position = tdNode?.text ?? ""
            
            let aNode2 = row.at_css("a[id^='ContentPlaceHolder1_gvPlayers_hl4yr_']")
            let commitment = aNode2?.text
            
            players.append(Player(name: playerName, position: position,
                                  href: href, commitment: commitment))
            //print("\(playerName),\(position),\(commitment),\(href)")
        }
        
        return players
    }
    
    //MARK: Utility Functions
    func extractYear(yearString: String) -> Int? {
        let words = yearString.components(separatedBy: " ")
        guard words.count == 3 else {
            return nil
        }
        
        let year:Int? = Int(words[2])
        return year
    }
    
}
