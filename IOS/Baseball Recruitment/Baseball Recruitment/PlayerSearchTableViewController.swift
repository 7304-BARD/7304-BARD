//
//  PlayerSearchTableViewController.swift
//  Baseball Recruitment
//
//  Created by Sergey Scott Nall  on 2/17/18.
//  Copyright © 2018 Team Bard. All rights reserved.
//

import UIKit

class PlayerSearchTableViewController: UITableViewController {

    let playerSearch = PlayerSearch()
    var players = [PlayerCard]()
    
    @IBOutlet weak var searchBar: UISearchBar!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return players.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "PlayerSearchCell", for: indexPath)

        let player = players[indexPath.row]
        let name = player.name
        let position = player.position
        let year = player.hsGrad
        let homeTown = player.homeTown
        let text = "\(name), \(position), \(year), \(homeTown)"
        cell.textLabel?.text = text

        return cell
    }
    

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "ToPlayerCardFromPlayerSearch" {
            guard let viewController = segue.destination as? PlayerCardViewController
                else {
                    fatalError("Unexpected destination: \(segue.destination)")
            }
            
            guard let selectedCell = sender as? UITableViewCell else {
                fatalError("Unexpected sender: \(String(describing: sender))")
            }
            
            guard let indexPath = tableView.indexPath(for: selectedCell) else {
                fatalError("The selected cell is not being displayed by the table")
            }
            
            let player = players[indexPath.row]
            viewController.hrefPath = player.hrefPath
        }
    }
 

}

extension PlayerSearchTableViewController : UISearchBarDelegate
{
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String)
    {
        if searchText.isEmpty {
            players.removeAll()
            tableView.reloadData()
            return
        }
        
        // If only a space has been added to the search, then do nothing.
        if searchText.suffix(1).trimmingCharacters(in: .whitespacesAndNewlines).isEmpty {
            return
        }
        
        playerSearch.requestSearch(search: searchText) { players in
            if players == nil {
                self.players.removeAll()
                return
            }
            
            self.players = Array<PlayerCard>(players!.prefix(10))
            DispatchQueue.main.async {
                self.tableView.reloadData()
            }
        }
    }
}
