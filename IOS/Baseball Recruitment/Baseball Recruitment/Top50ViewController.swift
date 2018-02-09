//
//  Top50ViewController.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 2/1/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit

class Top50ViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    //MARK: Properties
    let scraper = Top50HighSchool()
    var selectedYear = "Select a class year"
    var players = Array<Top50HighSchool.Player>()
    
    @IBOutlet weak var yearPicker: UITableView!
    @IBOutlet weak var playersTable: UITableView!
    
    //MARK: override functions
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    //MARK: TableView functions
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if tableView === playersTable {
            return players.count
        }
        
        return 1
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if tableView === yearPicker {
            let cellIdentifier = "Top50ClassOf"
            
            let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath)
            cell.textLabel?.text = selectedYear
            
            return cell
        }
        
        else if tableView === playersTable {
            let cellIdentifier = "PlayersCell"
            
            guard let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as? Top50HsTableViewCell else {
                fatalError("Not a valid top 50 HS players cell.")
            }
            
            let player = players[indexPath.row]
            
            cell.playerName.text = player.name
            cell.playerPosition.text = player.position
            cell.playerCommitment.text = player.commitment ?? ""
            
            return cell
        }
        
        fatalError("Unknown tableview.")
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        if tableView === yearPicker {
            return true
        }
        
        return true
    }

    
    // MARK: Navigation

    /*
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    
    //MARK: Actions
    @IBAction func unwindToTop50(sender: UIStoryboardSegue) {
        guard let sourceViewController = sender.source as?
            ClassOfTableViewController else {
            return
        }
        
        guard let index = sourceViewController.tableView.indexPathForSelectedRow
            else {
            return
        }
        
        selectedYear = sourceViewController.classOfYears[index.row]
        let index2 = yearPicker.indexPathsForSelectedRows!
        yearPicker.reloadRows(at: index2, with: .automatic)
        
        guard let year = scraper.extractYear(yearString: selectedYear) else {
            return
        }
        
        scraper.requestTopHighSchool(year: year) { year, players in            
            let slice = players.prefix(50)
            
            self.players = Array<Top50HighSchool.Player>(slice)
            
            DispatchQueue.main.async() {
                self.playersTable.reloadData()
            }
        }
    }
}
