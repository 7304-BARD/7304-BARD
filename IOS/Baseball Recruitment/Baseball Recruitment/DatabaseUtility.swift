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
    
    struct PlayerTableKeys
    {
        static let id = Expression<Int64>("id")
        static let name = Expression<String>("name")
        static let href = Expression<String>("href")
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
        let hsGradYear = PlayerTableKeys.hsGradYear
        let commitment = PlayerTableKeys.commitment
        
        let players = Table("players")
        try db.run(players.create(ifNotExists: true) { t in
            t.column(id, primaryKey: .autoincrement)
            t.column(name)
            t.column(href)
            t.column(hsGradYear)
            t.column(commitment)
        })
    }
    
    func addPlayer(player : PlayerCard) throws
    {
        func checkEmpty(_ s : String) -> String? {
            return s.isEmpty ? nil : s
        }
        
        guard let name = checkEmpty(player.name) else {
            throw DatabaseUtilityError.RuntimeError("Player name is empty.")
        }
        
        guard let href = checkEmpty(player.hrefPath) else {
            throw DatabaseUtilityError.RuntimeError("Player href is empty.")
        }
        
        var hsGradYear : Int64?
        if checkEmpty(player.hsGrad) != nil {
            hsGradYear = Int64(player.hsGrad)
        }
        
        let commitment = checkEmpty(player.commitment)
        
        let players = Table("players")
        
        try db.run(players.insert(PlayerTableKeys.name <- name,
                                  PlayerTableKeys.href <- href,
                                  PlayerTableKeys.hsGradYear <- hsGradYear,
                                  PlayerTableKeys.commitment <- commitment))
    }
    
    func getAllPlayers() throws -> [PlayerCard]? {
        var players = [PlayerCard]()
        
        for player in try db.prepare(Table("players")) {
            let playerCard = PlayerCard()
            playerCard.name = player[PlayerTableKeys.name]
            playerCard.hrefPath = player[PlayerTableKeys.href]
            playerCard.hsGrad = String(describing: player[PlayerTableKeys.hsGradYear])
            playerCard.commitment = player[PlayerTableKeys.commitment] ?? ""
            
            players.append(playerCard)
        }
        
        return players.isEmpty ? nil : players
    }
}
