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
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath)
        
        let tournament = tournamentList.tournaments[indexPath.row]
        let name = tournament.name
        let date = tournament.date
        let location = tournament.location
        
        cell.textLabel?.text = "\(name), \(date), \(location)"
        
        return cell
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
