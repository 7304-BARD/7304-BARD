//
//  DatabaseUtility.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 2/15/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import Foundation
import SQLite

enum DatabaseUtilityError : Error {
    case RuntimeError(String)
}

class DatabaseUtility
{
    static let databaseDirectory = FileManager().urls(for: .libraryDirectory, in: .userDomainMask).first!
    static let playerDatabaseUrl = databaseDirectory.appendingPathComponent("recruitment_db.sqlite3")
    var db : Connection!
    
    struct Table
    {
        static let players = SQLite.Table("players")
    }
    
    struct PlayerTableKeys
    {
        static let id = Expression<Int64>("id")
        static let name = Expression<String>("name")
        static let href = Expression<String>("href")
        static let position = Expression<String?>("position")
        static let hsGradYear = Expression<Int64?>("hs_grad_year")
        static let commitment = Expression<String?>("commitment")
    }
    
    init?()
    {
        guard (try? (db = connect())) != nil else {
            return nil
        }
        
        db.busyTimeout = 10
    }
    
    func connect() throws -> Connection
    {
        let path = "\(DatabaseUtility.playerDatabaseUrl)"
        let db = try Connection(path)
        
        return db
    }
    
    func createPlayerTable() throws
    {
        let id = PlayerTableKeys.id
        let name = PlayerTableKeys.name
        let href = PlayerTableKeys.href
        let position = PlayerTableKeys.position
        let hsGradYear = PlayerTableKeys.hsGradYear
        let commitment = PlayerTableKeys.commitment
        
        try db.run(Table.players.create(ifNotExists: true) { t in
            t.column(id, primaryKey: .autoincrement)
            t.column(name)
            t.column(href)
            t.column(position)
            t.column(hsGradYear)
            t.column(commitment)
            t.unique(name, href)
        })
    }
    
    func addPlayer(player : PlayerCard) throws
    {
        func checkEmpty(_ s : String) -> String? {
            return s.isEmpty ? nil : s
        }
        
        guard let name = checkEmpty(player.name) else {
            print("Player name is empty.")
            return
        }
        
        guard let href = checkEmpty(player.hrefPath) else {
            print("Player href is empty.")
            return
        }
        
        let position = checkEmpty(player.position)
        
        var hsGradYear : Int64?
        if checkEmpty(player.hsGrad) != nil {
            hsGradYear = Int64(player.hsGrad)
        }
        
        let commitment = checkEmpty(player.commitment)
        
        try db.run(Table.players.insert(PlayerTableKeys.name <- name,
                                  PlayerTableKeys.href <- href,
                                  PlayerTableKeys.position <- position,
                                  PlayerTableKeys.hsGradYear <- hsGradYear,
                                  PlayerTableKeys.commitment <- commitment))
    }
    
    // Get a player from a database row.
    func getPlayerCard(_ player: Row) -> PlayerCard
    {
        let playerCard = PlayerCard()
        playerCard.name = player[PlayerTableKeys.name]
        playerCard.hrefPath = player[PlayerTableKeys.href]
        playerCard.position = player[PlayerTableKeys.position] ?? ""
        
        let hsGrad = player[PlayerTableKeys.hsGradYear]
        playerCard.hsGrad = (hsGrad != nil) ? String(hsGrad!) : ""
        
        playerCard.commitment = player[PlayerTableKeys.commitment] ?? ""
        
        return playerCard
    }
    
    func getAllPlayers() throws -> [PlayerCard]?
    {
        var players = [PlayerCard]()
        
        for player in try db.prepare(Table.players) {
            let playerCard = getPlayerCard(player)
            players.append(playerCard)
        }
        
        return players.isEmpty ? nil : players
    }
    
    // Returns the first player with a certain name or name/href combo.
    func getPlayer(name: String, href: String?) throws -> PlayerCard?
    {
        let keyName = PlayerTableKeys.name
        let keyHref = PlayerTableKeys.href
        
        var query = Table.players.filter(keyName == name)
        if let href = href {
            query = Table.players.filter(keyName == name && keyHref == href)
        }
        
        guard let player = try db.pluck(query) else {
            return nil
        }
        
        let playerCard = getPlayerCard(player)
        return playerCard
    }
    
    func playerInDatabase(name: String, href: String?) -> Bool
    {
        guard ((try! getPlayer(name: name, href: href)) != nil) else {
            return false
        }
        
        return true
    }
    
    func deletePlayer(name: String, href: String?) throws
    {
        let keyName = PlayerTableKeys.name
        let keyHref = PlayerTableKeys.href
        
        var query = Table.players.filter(keyName == name)
        if let href = href {
            query = Table.players.filter(keyName == name && keyHref == href)
        }
        
        try db.run(query.delete())
    }
    
    static func deletePlayerDatabase()
    {
        let fileManager = FileManager.default
        
        do {
            try fileManager.removeItem(at: DatabaseUtility.playerDatabaseUrl)
            print("Deleted database as \(DatabaseUtility.playerDatabaseUrl)")
        } catch let error as NSError {
            print("Could not delete player database. Error: \(error)")
        }
    }
}
