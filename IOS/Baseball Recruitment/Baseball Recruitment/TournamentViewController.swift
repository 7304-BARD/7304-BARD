//
//  TournamentViewController.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 2/27/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit

class TournamentViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    // MARK: Properties
    @IBOutlet weak var yearTextField: UITextField!
    @IBOutlet weak var monthTextField: UITextField!
    @IBOutlet weak var stateTextField: UITextField!
    @IBOutlet weak var divisionTextField: UITextField!
    @IBOutlet weak var tournamentTableView: UITableView!
    @IBOutlet weak var monthButton: UIButton!
    
    var tournamentList = TournamentList()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        tournamentList.request {
            DispatchQueue.main.async {
                self.yearTextField.text = self.tournamentList.getSelectedYear()
                self.monthTextField.text = self.tournamentList.getSelectedMonth()
                self.stateTextField.text = self.tournamentList.getSelectedState()
                self.divisionTextField.text = self.tournamentList.getSelectedDivision()
                self.tournamentTableView.reloadData()
            }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    //MARK: TableView functions
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return tournamentList.tournaments.count
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        // XXX We will have sections for every block month later.
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellIdentifier = "TournamentCell"
        guard let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as? TournamentTableViewCell else {
            fatalError("Could not get TournamentViewCell")
        }
        
        let tournament = tournamentList.tournaments[indexPath.row]
        let name = tournament.name
        let date = tournament.date
        let location = tournament.location
        
        cell.tournamentLabel.text = name
        cell.locationLabel.text = location
        cell.dateLabel.text = date
        
        //cell.textLabel?.text = "\(name), \(date), \(location)"
        
        return cell
    }

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if let destination = segue.destination as? SimpleOptionsTableViewController {
            guard let button = sender as? UIButton else {
                print("Could not get button")
                return
            }
            
            if button === monthButton {
                destination.selectionType = .TournamentMonth
                for (text, value) in tournamentList.months {
                    destination.rows.append((text, value))
                }
            }
        }
        
    }
    
    // MARK: Actions
    @IBAction func pressChangeMonth(_ sender: UIButton) {
        performSegue(withIdentifier: "ChangeOptions", sender: sender)
    }
    
    @IBAction func unwindToTournamentView(sender: UIStoryboardSegue) {
        guard let sourceViewController = sender.source as?
            SimpleOptionsTableViewController else {
                return
        }

        guard let index = sourceViewController.tableView.indexPathForSelectedRow
            else {
                return
        }

        let (text, value) = sourceViewController.rows[index.row]
        let selectionType = sourceViewController.selectionType
        
        if selectionType == .TournamentMonth {
            monthTextField.text = text
            tournamentList.postMonth(value: value) {
                DispatchQueue.main.async {
                    self.tournamentTableView.reloadData()
                }
            }
        }
    }
    
}
