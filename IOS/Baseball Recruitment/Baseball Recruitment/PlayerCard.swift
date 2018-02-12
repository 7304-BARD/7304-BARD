//
//  PlayerCard.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 2/8/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit
import Kanna
import Alamofire
import AlamofireImage

class PlayerCard {
    //MARK: Properties
    var hrefPath : String = ""
    var name : String = ""
    var hsGrad : String = ""
    var age : String = ""
    var ageAtDraft = ""
    var position: String = ""
    var positionOther: String = ""
    var height : String = ""
    var weight : String = ""
    var battingAndThrowing : String = ""
    var highSchool : String = ""
    var homeTown : String = ""
    var summerTeam : String = ""
    var fallTeam : String = ""
    var commitment: String = ""
    var image : UIImage?
    var imagePath : String = ""
    
    //MARK: Player Card Scraping
    func requestPlayerCard(hrefPath: String,
                           completion: @escaping () -> ()) {
        
        self.hrefPath = hrefPath
        let mainUrl = "https://www.perfectgame.org/"
        let url = mainUrl + hrefPath
        
        Alamofire.request(url).responseString { response in
            guard response.result.isSuccess else {
                print("Alamofire.request failed.\n\(String(describing: response.error))")
                return
            }
            
            if let html = response.result.value {
                self.scrapePlayerCard(html: html)
                completion()
            }
        }
    }
    
    func scrapePlayerCard(html: String) {
        guard let doc = Kanna.HTML(html: html, encoding: String.Encoding.utf8)
            else {
                print("Kana.HTML failed.")
                return
        }
        
        var selector : String = ""
        
        // Name
        selector = "#ContentPlaceHolder1_Bio1_lblName"
        if let node = doc.at_css(selector) {
            self.name = node.text ?? ""
        }
        
        // Image Path
        selector = "#ContentPlaceHolder1_Bio1_imgMainPlayerImage"
        if let node = doc.at_css(selector) {
            self.imagePath = node["src"] ?? ""
        }
        
        // HS Grad
        selector = "#ContentPlaceHolder1_Bio1_lblGradYear"
        if let node = doc.at_css(selector) {
            self.hsGrad = node.text ?? ""
        }
        
        // Current Age
        selector = "#ContentPlaceHolder1_Bio1_lblAgeNow"
        if let node = doc.at_css(selector) {
            self.age = node.text ?? ""
        }
        
        // Age at Draft (not all players have this field)
        selector = "#ContentPlaceHolder1_Bio1_lblAgeAtDraft"
        if let node = doc.at_css(selector) {
            self.ageAtDraft = node.text ?? ""
        }
        
        // Position
        selector = "#ContentPlaceHolder1_Bio1_lblPrimaryPosition"
        if let node = doc.at_css(selector) {
            self.position = node.text ?? ""
        }
        
        // Position Other
        selector = "#ContentPlaceHolder1_Bio1_lblOtherPositions"
        if let node = doc.at_css(selector) {
            self.positionOther = node.text ?? ""
        }
        
        // Height
        selector = "#ContentPlaceHolder1_Bio1_lblHeight"
        if let node = doc.at_css(selector) {
            self.height = node.text ?? ""
        }
        
        // Weight
        selector = "#ContentPlaceHolder1_Bio1_lblWeight"
        if let node = doc.at_css(selector) {
            self.weight = node.text ?? ""
        }
        
        // Batting and Throwing
        selector = "#ContentPlaceHolder1_Bio1_lblBatsThrows"
        if let node = doc.at_css(selector) {
            self.battingAndThrowing = node.text ?? ""
        }
        
        // High School
        selector = "#ContentPlaceHolder1_Bio1_lblHS"
        if let node = doc.at_css(selector) {
            self.highSchool = node.text ?? ""
        }
        
        // Home Town
        selector = "#ContentPlaceHolder1_Bio1_lblHomeTown"
        if let node = doc.at_css(selector) {
            self.homeTown = node.text ?? ""
        }
        
        // Summer Team
        selector = "#ContentPlaceHolder1_Bio1_lblSummerTeam"
        if let node = doc.at_css(selector) {
            self.summerTeam = node.text ?? ""
        }
        
        // Fall Team
        selector = "#ContentPlaceHolder1_Bio1_lblFallTeam"
        if let node = doc.at_css(selector) {
            self.fallTeam = node.text ?? ""
        }
    }
    
    func requestPlayerImage(url: String, completion: @escaping () -> ()) {
        Alamofire.request(url).responseImage { response in
            guard response.result.isSuccess else {
                print("Alamofire.request for image failed.\n\(String(describing: response.error))")
                return
            }
            
            self.image = response.result.value
            completion()
        }
    }
}
