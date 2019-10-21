import json
import sys
import os
import itertools

#This still needs some work
def isInvalidBatch(data):
    """Determines if the deck of cards is valid
    
    Arguments:
        data {list} -- [list containing the cards]
    """
    sorted_cards ={10:[], 9:[], 8:[], 7:[], 6:[], 5:[], 4:[], 3:[], 2:[],
                    'A':[], 'K':[], 'Q':[], 'J':[] }
    if len(data) != 52:
        print("Invalid: deck does not conatin 52 cards")
    
    for d in data:
        #checking 10's
        if len(d) == 3:
            sorted_cards[int(d[:2])].append(d) 
        #checking digit rank
        if str.isdigit(d[0]):
            if int(d[0]) >= 2:
                sorted_cards[int(d[0])].append(d)
        #must be char rank
        else:
            sorted_cards[d[0]].append(d)

    # sorted_cards now contains each rank of card
    # with a list that should contain 4 elements
    print(sorted_cards)
    #check all 4 suits are contained in the card
    for key, value in sorted_cards.items():
        print(value)
        for card in value:
            print(card[len(card)-1])

def caluclateDistance(first_card, second_card):
    """Calculates the distance based on the difference in the rank 
        The rank of each entry is determined by its initial character(s), where:
        the rank of "A" equals 1; the rank of "2" through "9" equals the
        corresponding integer; and the rank of "10", "J", "Q", and "K" equals 10.

    Arguments:
        first_card {string} -- previous card
        second_card {string} -- succesive card
    
    Returns:
        int -- absolute value of the difference in rank
    """
    face_cards = ['K', 'Q', 'J']
    first_val = 0
    second_val = 0
    #Handle Aces
    if first_card[0] == 'A':
        first_val = 1
    if second_card[0] == 'A':
        second_val = 1
    #Handle face cards and 10's
    if first_card[0] in face_cards or len(first_card) == 3:
        first_val = 10
    if second_card[0] in face_cards or len(second_card) == 3:
        second_val = 10
    #Handle number cards
    if str.isdigit(first_card[0]) and int(first_card[0]) > 1:
        first_val = int(first_card[0])
    if str.isdigit(second_card[0]) and int(second_card[0]) > 1:
        second_val=int(second_card[0])

    return abs(second_val - first_val)

def calculateWaste(data):
    """Calculates the total waste value based on the following critria:
        1. the difference of the ranks of the two entries, when the entries share the same suit;
        2. twice the difference of the ranks of the two entries, when the entries share the same color;
        3. three times the difference of the ranks of the two entries, when the entries have different colors.

    Arguments:
        data {list} -- list containg the cards
    
    Returns:
        int -- total waste value of the list
    """
    red_suits = ['H', 'D']
    black_suits  = ['C', 'S']
    total_distance = 0
    for i in range(len(data)-1):
        suit = data[i][len(data[i])-1]
        next_suit = data[i+1][len(data[i+1])-1]
        #if the suits are the same
        if suit == next_suit:
            total_distance += caluclateDistance(data[i], data[i+1])
        #if the colors are the same twice the distance value
        elif (suit in red_suits and next_suit in red_suits) or (suit in black_suits and next_suit in black_suits):
                total_distance += (2 * caluclateDistance(data[i], data[i+1]))
        #different suit different color
        else:
            total_distance += (3 * caluclateDistance(data[i], data[i+1]))

    print("The total waste value is", total_distance)
    return total_distance



 
 

def findPostions(data):
   a=set(itertools.combinations(list(range(0, 53)), 2))
   print(sorted(a))
    
    
          


def main(argv):
    if len(argv) != 2:
        print('Usage: %s [JSON file]' % argv[0])
        sys.exit(0)
    try:
        data_path =  os.path.relpath('../sample-data/' + argv[1])
        with open(data_path, 'r') as f:
            batch_data = json.load(f)
    except:
        print('failed to open file %s' % argv[1])
        sys.exit(0)


    #calculateWaste(batch_data)
    #isInvalidBatch(batch_data)
    findPostions(batch_data)


if __name__ == '__main__':
    main(sys.argv)





