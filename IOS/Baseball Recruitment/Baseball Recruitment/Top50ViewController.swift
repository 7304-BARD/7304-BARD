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
    var selectedYear = "Select a class year"
    
    @IBOutlet weak var yearPicker: UITableView!
    
    
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
        return 1
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellIdentifier = "Top50ClassOf"
        
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath)
        cell.textLabel?.text = selectedYear
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
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
    }
    


}
