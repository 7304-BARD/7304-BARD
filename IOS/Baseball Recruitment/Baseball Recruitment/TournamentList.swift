//
//  Tournament.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 2/27/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit
import Kanna
import Alamofire

struct Tournament
{
    var id : String = ""
    var name : String = ""
    var date : String = ""
    var location : String = ""
    var divisions : [String] = [String]()
    var blockMonth : String = ""
}

class TournamentList
    
{
    // MARK Properties
    
    // Each variable below is an array of String pairs.
    // The first element of each pair is human readable text.
    // The second element of each pair is an encoded value that can be
    // used for an HTTP POST request.
    var years = [(text: String, value: String)]()
    let months : [(text: String, value: String)] = [
        ("January", "ctl00$ContentPlaceHolder1$lbFirst"),
        ("February", "ctl00$ContentPlaceHolder1$lbSecond"),
        ("March", "ctl00$ContentPlaceHolder1$lbThird"),
        ("April", "ctl00$ContentPlaceHolder1$lbFourth"),
        ("May", "ctl00$ContentPlaceHolder1$lbFifth"),
        ("June", "ctl00$ContentPlaceHolder1$lbSixth"),
        ("July", "ctl00$ContentPlaceHolder1$lbSeventh"),
        ("August", "ctl00$ContentPlaceHolder1$lbEighth"),
        ("September", "ctl00$ContentPlaceHolder1$lbNinth"),
        ("October", "ctl00$ContentPlaceHolder1$lbTenth"),
        ("November", "ctl00$ContentPlaceHolder1$lbEleventh"),
        ("December", "ctl00$ContentPlaceHolder1$lbTwelveth")
    ]
    
    var states = [(text: String, value: String)]()
    var divisions = [(text: String, value: String)]()
    var postParameters : [String : String] = [
        "__ASYNCPOST" : "false",
        "__EVENTARGUMENT" : "",
        "__EVENTTARGET" : "",
        "__EVENTVALIDATION" : "",
        "__LASTFOCUS" : "",
        "__VIEWSTATE" : "",
        "__VIEWSTATEGENERATOR" : "",
        "ctl00$ContentPlaceHolder1$ddlAgeDivision" : "0",
        "ctl00$ContentPlaceHolder1$ddlState" : "ZZ",
        "ctl00$ContentPlaceHolder1$ddlYear" : "2018",
        "ctl00$ContentPlaceHolder1$rblTournaments" : "1,2,3"
    ]
    
    var tournaments = [Tournament]()
    
    // MARK Helper functions
    func getSelectedYear() -> String {
        let year = postParameters["ctl00$ContentPlaceHolder1$ddlYear"]
        for (text, value) in years {
            if year == value {
                return text
            }
        }
        
        return DateTime.getDateString("yyyy")
    }
    
    func getSelectedMonth() -> String {
        // XXX For now, it will just be the current month.
        let month = DateTime.getDateString("MM")
        let index = Int(month)! - 1
        return months[index].text
    }
    
    func getSelectedState() -> String {
        let selected = postParameters["ctl00$ContentPlaceHolder1$ddlState"]
        for (text, value) in states {
            if selected == value {
                return text
            }
        }
        
        return "All States"
    }
    
    func getSelectedDivision() -> String {
        let selected = postParameters["ctl00$ContentPlaceHolder1$ddlAgeDivision"]
        for (text, value) in divisions {
            if selected == value {
                return text
            }
        }
        
        return "All Age Divisions"
    }
    
    // MARK Scraping
    func request(completion: @escaping () -> ()) {
        let url = "https://www.perfectgame.org/Schedule/Default.aspx?Type=Tournaments"
        
        Alamofire.request(url).responseString { response in
            guard response.result.isSuccess else {
                print("Alamofire.request failed.\n\(String(describing: response.error))")
                return
            }
            
            guard let html = response.result.value else {
                print("response.result.value is nil.")
                return
            }
            
            guard let doc = Kanna.HTML(html: html, encoding: String.Encoding.utf8)
                else {
                    print("Kana.HTML failed.")
                    return
            }

            self.scrapePostParameters(doc)
            self.scrapeYears(doc)
            self.scrapeMonths(doc)
            self.scrapeStates(doc)
            self.scrapeDivisions(doc)
            self.scrapeTournaments(doc)
            
            completion()
        }
    }
    
    func scrapeYears(_ doc: HTMLDocument) {
        years = scrapeOptionTextValues(doc, "#ContentPlaceHolder1_ddlYear")
    }
    
    func scrapeMonths(_ doc: HTMLDocument) {
        // Does nothing for now.
    }
    
    func scrapeStates(_ doc: HTMLDocument) {
        states = scrapeOptionTextValues(doc, "#ContentPlaceHolder1_ddlState")
    }
    
    func scrapeDivisions(_ doc: HTMLDocument) {
        divisions = scrapeOptionTextValues(doc, "#ContentPlaceHolder1_ddlAgeDivision")
    }
    
    func scrapePostParameters(_ doc: HTMLDocument) {
        if let node = doc.at_css("#__EVENTARGUMENT") {
            postParameters["__EVENTARGUMENT"] = node["value"] ?? ""
        }
        
        if let node = doc.at_css("#__EVENTTARGET") {
            postParameters["__EVENTTARGET"] = node["value"] ?? ""
        }
        
        if let node = doc.at_css("#__EVENTVALIDATION") {
            postParameters["__EVENTVALIDATION"] = node["value"] ?? ""
        }
        
        if let node = doc.at_css("#__LASTFOCUS") {
            postParameters["__EVENTTARGET"] = node["value"] ?? ""
        }
        
        if let node = doc.at_css("#__VIEWSTATE") {
            postParameters["__VIEWSTATE"] = node["value"] ?? ""
        }
        
        if let node = doc.at_css("#__VIEWSTATEGENERATOR") {
            postParameters["__VIEWSTATEGENERATOR"] = node["value"] ?? ""
        }
        
        var year = scrapeSelectedOption(doc, "#ContentPlaceHolder1_ddlYear")
        if year != nil {
            postParameters["ctl00$ContentPlaceHolder1$ddlYear"] = year!
        } else {
            year = DateTime.getDateString("yyyy")
            postParameters["ctl00$ContentPlaceHolder1$ddlYear"] = year
        }
        
        let state = scrapeSelectedOption(doc, "#ContentPlaceHolder1_ddlState")
        postParameters["ctl00$ContentPlaceHolder1$ddlState"] = state ?? "ZZ"
        
        let division = scrapeSelectedOption(doc, "#ContentPlaceHolder1_ddlAgeDivision")
        postParameters["ctl00$ContentPlaceHolder1$ddlAgeDivision"] = division ?? "0"
    }
    
    func scrapeTournaments(_ doc: HTMLDocument) {
        var index = 0
        while true {
            var tournament = Tournament()
            
            var selector = "#ContentPlaceHolder1_repSchedule_hlEventName_\(index)"
            guard let name = doc.at_css(selector) else {
                return
            }
            tournament.name = name["value"] ?? ""
            
            selector = "#ContentPlaceHolder1_repSchedule_hlEventDate_\(index)"
            guard let eventDate = doc.at_css(selector) else {
                return
            }
            tournament.date = eventDate["value"] ?? ""
            
            selector = "#ContentPlaceHolder1_repSchedule_hlEventLocation_\(index)"
            if let location = doc.at_css(selector) {
                tournament.location = location["value"] ?? ""
            }
            
            selector = "#ContentPlaceHolder1_repSchedule_hlBlockMonth_\(index)"
            if let blockMonth = doc.at_css(selector) {
                tournament.blockMonth = blockMonth["value"] ?? ""
            }
            
            tournaments.append(tournament)
            index += 1
        }
    }
    
    func scrapeOptionTextValues(_ doc: HTMLDocument, _ id: String) -> [(String, String)] {
        var output = [(String, String)]()
        
        guard let selectNode = doc.at_css(id) else {
            return output
        }
        
        let options = selectNode.css("option")
        for option in options {
            let text = option.text ?? ""
            let value = option["value"] ?? ""
            
            output.append((text, value))
        }
        
        return output
    }
    
    // Returns the encoded value, not the text.
    func scrapeSelectedOption(_ doc: HTMLDocument, _ id: String) -> String? {
        guard let selectNode = doc.at_css(id) else {
            return nil
        }
        
        guard let option = selectNode.at_css("option[selected='selected']") else {
            return nil
        }
        
        return option["value"]
    }
}
